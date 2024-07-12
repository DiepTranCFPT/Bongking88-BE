package com.example.demo.service;

import com.example.demo.eNum.*;
import com.example.demo.entity.*;
import com.example.demo.exception.GlobalException;
import com.example.demo.model.Request.BookingDetailRequest;
import com.example.demo.model.Request.BookingRequest;
import com.example.demo.respository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@AllArgsConstructor
public class BookingService {



    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CourtSlotRepository courtSlotRepository;


    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private CourtRepository courtRepository;

    @Autowired
    BookingDetailRepository bookingDetailRepository;

    @Autowired
    AuthenticationRepository authenticationRepository;

    @Autowired
    PromotionRepository promotionRepository;

    @Autowired
    LocationRepository locationRepository;



    public List<Booking> getBookingsByCustomerId(long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }
    public Booking getBooking(long Id) {
        return bookingRepository.findById(Id).orElseThrow(() -> new GlobalException("booking không tồn tại"));
    }

    public double price(BookingRequest bookingRequest) {
        double amuont = 0;
        for (BookingDetailRequest bookingDetailRequest :bookingRequest.getBookingDetailRequests()) {
            Slot slot = slotRepository.findById(bookingDetailRequest.getIdSlot()).orElseThrow(() -> new GlobalException("Slot not found"));
            amuont += slot.getPrice();
        }
        if(bookingRequest.getBookingType().equals(BookingTypeEnum.FIXED)){
            amuont = amuont * 0.9;
        }else if(bookingRequest.getBookingType().equals(BookingTypeEnum.FLEXIBLE)){
            amuont = amuont * 0.95;
        }
        Promotion promotion = promotionRepository.findByIdAndStatus(bookingRequest.getIdPromotion(),PromotionStatus.ACTIVE);

        if(promotion != null){
            amuont = amuont  - promotion.getDiscount();
        }

        return amuont;
    }

    public static String formatDateString(String dateStr) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("M-d-yyyy");
        Date date = inputFormat.parse(dateStr);
        SimpleDateFormat outputFormat = new SimpleDateFormat("MM-dd-yyyy");
        return outputFormat.format(date);
    }

    public Booking createBooking(BookingRequest bookingRequest) throws ParseException {

        Random random = new Random();
        Booking booking = new Booking();
        double amuont = 0;

        if(bookingRequest.getBookingType().equals(BookingTypeEnum.FLEXIBLE)){
            if(bookingRequest.getBookingDetailRequests().size() < 5) throw new GlobalException("Booking linh hoạt phải cần có đặt ít nhất 5 slot");
        }

        Location location = locationRepository.findByIdAndStatus(bookingRequest.getIdLocation(),ClubStatus.ACTIVE).orElseThrow(() -> new GlobalException("location InActive"));

        Account account = authenticationRepository.findById(bookingRequest.getIdUser()).orElseThrow(() -> new GlobalException("không có user"));

        if(account.getWallet().getAmount() < price(bookingRequest)){
            throw new GlobalException("ví không đủ số dư để tạo booking");
        }
        List<BookingDetail> bookingDetails = new ArrayList<BookingDetail>();

        List<CourtSlot> courtSlotList = new ArrayList<>();



        for (BookingDetailRequest bookingDetailRequest :bookingRequest.getBookingDetailRequests()) {

            String date  = formatDateString(bookingDetailRequest.getDate());


            BookingDetail bookingDetail = new BookingDetail();
            CourtSlot newCourtSlot = new CourtSlot();

            Slot slot = slotRepository.findById(bookingDetailRequest.getIdSlot()).orElseThrow(() -> new GlobalException("Slot not found"));
            if (slot.getStatus().equals(SlotStatus.INACTIVE)) throw new GlobalException("SLot không hoạt động");
            List<CourtSlot> courtSlot = courtSlotRepository.findBySlotIdAndDate(slot.getId(), date).stream().filter(cs -> cs.getStatus().equals(CourtSlotStatus.PENDING)).toList();

            List<Long> idCourts = new ArrayList<>();
            for (CourtSlot listCourtSlot : courtSlot) {
                idCourts.add(listCourtSlot.getCourt().getId());
            }

            List<Court> courts = courtRepository.findByIdNotInAndLocationId(idCourts,bookingRequest.getIdLocation()).stream().filter(court -> court.getStatus().equals(CourtStatus.ACTIVE)).toList();
            if(courts.isEmpty()) {
                throw  new GlobalException(bookingDetailRequest.getDate() + " ngày này với " + slot.getTime() + " không còn sân");
            }


            newCourtSlot.setDate(date);
            newCourtSlot.setStatus(CourtSlotStatus.UNSUCCESSFUL);
            newCourtSlot.setSlot(slot);
            Court randomCourt = courts.get(random.nextInt(courts.size()));
            newCourtSlot.setCourt(randomCourt);
            newCourtSlot.setBookingDetail(bookingDetail);
            newCourtSlot.setAccount(account);
            newCourtSlot = courtSlotRepository.save(newCourtSlot);
            courtSlotList.add(newCourtSlot);

            bookingDetail.setBooking(booking);
            bookingDetail.setPrice(slot.getPrice());
            bookingDetail.setCourtSlot(newCourtSlot);
            bookingDetails.add(bookingDetail);
            amuont += slot.getPrice();
        }

            if(bookingRequest.getBookingType().equals(BookingTypeEnum.FIXED)){
                booking.setBookingType(BookingTypeEnum.FIXED);
                amuont = amuont * 0.9;
            }else if(bookingRequest.getBookingType().equals(BookingTypeEnum.SLOT)){
                booking.setBookingType(BookingTypeEnum.SLOT);
            }else{
                booking.setBookingType(BookingTypeEnum.FLEXIBLE);
                amuont = amuont * 0.95;
            }


            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
            String formattedDate = dateFormat.format(currentDate);

            booking.setBookingDetails(bookingDetails);
            booking.setBookingDate(formattedDate);
            booking.setStatus(BookingStatus.SUCCESS);
            booking.setCustomer(account);

            Promotion promotion = promotionRepository.findByIdAndStatus(bookingRequest.getIdPromotion(),PromotionStatus.ACTIVE);

            if(promotion != null){
                amuont = amuont  - promotion.getDiscount();
                booking.setPromotion(promotion);
            }

            if(account.getWallet().getAmount() < amuont){
                throw new GlobalException("ví không đủ số dư để tạo booking");
            }

            booking.setTotalPrice(String.valueOf(amuont));

            Account admin = location.getOwner();
            admin.getWallet().setAmount(admin.getWallet().getAmount() + amuont);

            booking.setLocation(location);
            account.getWallet().setAmount(account.getWallet().getAmount() - amuont);

            admin = authenticationRepository.save(admin);
            account = authenticationRepository.save(account);
            booking = bookingRepository.save(booking);

                for(CourtSlot courtSlot : courtSlotList){
                    courtSlot.setStatus(CourtSlotStatus.PENDING);
                    courtSlotRepository.save(courtSlot);
                }
        return booking;
    }


    }




