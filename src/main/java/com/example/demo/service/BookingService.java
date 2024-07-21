package com.example.demo.service;

import com.example.demo.eNum.*;
import com.example.demo.entity.*;
import com.example.demo.exception.GlobalException;
import com.example.demo.model.Request.BookingDetailRequest;
import com.example.demo.model.Request.BookingRequest;
import com.example.demo.respository.*;
import lombok.AllArgsConstructor;
import org.hibernate.Transaction;
import org.hibernate.engine.transaction.spi.TransactionObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BookingService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private BookingRepository bookingRepository;

    private CourtSlotRepository courtSlotRepository;

    private SlotRepository slotRepository;

    private CourtRepository courtRepository;

    private BookingDetailRepository bookingDetailRepository;

    private AuthenticationRepository authenticationRepository;

    private PromotionRepository promotionRepository;

    private LocationRepository locationRepository;

    private TransactionService transactionService;


    @Autowired
    public BookingService(BookingRepository bookingRepository, CourtSlotRepository courtSlotRepository,
                          SlotRepository slotRepository, CourtRepository courtRepository,
                          BookingDetailRepository bookingDetailRepository, AuthenticationRepository authenticationRepository,
                          PromotionRepository promotionRepository, LocationRepository locationRepository,
                          TransactionService transactionService, WalletRepository walletRepository, TransactionRepository transactionRepository) {
        this.bookingRepository = bookingRepository;
        this.courtSlotRepository = courtSlotRepository;
        this.slotRepository = slotRepository;
        this.courtRepository = courtRepository;
        this.bookingDetailRepository = bookingDetailRepository;
        this.authenticationRepository = authenticationRepository;
        this.promotionRepository = promotionRepository;
        this.locationRepository = locationRepository;
        this.transactionService = transactionService;
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }


    public List<Booking> getBookingsByCustomerId(long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    public Booking getBooking(long Id) {
        return bookingRepository.findById(Id).orElseThrow(() -> new GlobalException("booking không tồn tại"));
    }

    public double price(BookingRequest bookingRequest) {
        double amuont = 0;
        for (BookingDetailRequest bookingDetailRequest : bookingRequest.getBookingDetailRequests()) {
                Slot slot = slotRepository.findById(bookingDetailRequest.getIdSlot()).orElseThrow(() -> new GlobalException("Slot not found"));
                amuont += slot.getPrice();

        }
        if (bookingRequest.getBookingType().equals(BookingTypeEnum.FIXED)) {
            amuont = amuont * 0.9;
        } else if (bookingRequest.getBookingType().equals(BookingTypeEnum.FLEXIBLE)) {
            amuont = amuont * 0.95;
        }
        Promotion promotion = promotionRepository.findByIdAndStatus(bookingRequest.getIdPromotion(), PromotionStatus.ACTIVE);

        if (promotion != null) {
            amuont = amuont - promotion.getDiscount();
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
        List<BookingDetail> bookingDetails = new ArrayList<>();

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
        admin.getWallet().setAmount(admin.getWallet().getAmount() + amuont*0.02);

        ////
        admin.getWallet().setTransactions(transactionService.AddTransaction(
                admin.getWallet().getTransactions(),admin.getWallet(),amuont*0.02,
                TransactionType.BOOKING_SUCCESS
        ));

        ////
        account.getWallet().setTransactions(transactionService.AddTransaction(
                account.getWallet().getTransactions(),account.getWallet()
                ,amuont,TransactionType.BOOKING_SUCCESS
        ));

        ///

        Account owner = location.getOwner();
        owner.getWallet().setAmount(owner.getWallet().getAmount() + amuont*0.98);
        owner.getWallet().setTransactions(transactionService.AddTransaction(
                owner.getWallet().getTransactions(),owner.getWallet(),amuont*0.98,
                TransactionType.BOOKING_SUCCESS
        ));



        ////
        booking.setCodebooking(generateRandomString(6));
        ////



        booking.setLocation(location);
        account.getWallet().setAmount(account.getWallet().getAmount() - amuont);

       authenticationRepository.save(admin);
       authenticationRepository.save(account);
       bookingRepository.save(booking);

        for(CourtSlot courtSlot : courtSlotList){
            courtSlot.setStatus(CourtSlotStatus.PENDING);
            courtSlotRepository.save(courtSlot);
        }
        return booking;
    }
    /**
     * Huy Lich co dinh va huy book choi ngay.
     *
     * @param idBooking
     * @return
     */
    public Booking CancelKookingFIXED(long idBooking) {
        Optional<Booking> booking = bookingRepository.findById(idBooking);
        if (booking.isPresent()) {
            booking.get().setStatus(BookingStatus.CANCEL);
            List<BookingDetail> bookingDetails = booking.get().getBookingDetails();
            for (BookingDetail detail : bookingDetails) {
                Slot slot = detail.getCourtSlot().getSlot();
                detail.getCourtSlot().setStatus(CourtSlotStatus.INACTIVE);
                slot.setStatus(SlotStatus.ACTIVE);
                slotRepository.save(slot);
                bookingDetailRepository.save(detail);
            }
            /// price Reture
            double priceReturn = Double.parseDouble(booking.get().getTotalPrice()) * 0.9;
            // tru tien onwer
            Account owner = booking.get().getLocation().getOwner();
            owner.getWallet().setAmount(owner.getWallet().getAmount() - priceReturn);
            owner.getWallet().setTransactions(transactionService.AddTransaction(
                    owner.getWallet().getTransactions(), owner.getWallet(), priceReturn, TransactionType.BOOKING_REJECT
            ));
            transactionRepository.saveAll(owner.getWallet().getTransactions());
            walletRepository.save(owner.getWallet());
            authenticationRepository.save(owner);


            // + tien customer
            Account account = booking.get().getCustomer();
            account.getWallet().setAmount(account.getWallet().getAmount() + priceReturn);
            account.getWallet().setTransactions(transactionService.AddTransaction(
                    account.getWallet().getTransactions(), account.getWallet(), priceReturn, TransactionType.BOOKING_REJECT
            ));
            transactionRepository.saveAll(account.getWallet().getTransactions());
            walletRepository.save(account.getWallet());
            authenticationRepository.save(account);
            return booking.get();
        }
        return null;
    }

    // Huy Slot trong lich co dinh
    public Booking CancelKookingFIXEDinSLOT(long idBooking, long idSlot) {
        Optional<Booking> booking = bookingRepository.findById(idBooking);

        if (booking.isPresent()) {
            List<BookingDetail> bookingDetails = booking.get().getBookingDetails();
            for (BookingDetail detail : bookingDetails) {
                CourtSlot courtSlot = detail.getCourtSlot();
                if (courtSlot.getId() == idSlot) {
                    courtSlot.setStatus(CourtSlotStatus.ACTIVE);
                    courtSlotRepository.save(courtSlot);
                    double priceReturn = courtSlot.getSlot().getPrice() * 0.9;
                    Account account = booking.get().getCustomer();
                    booking.get().getCustomer().getWallet().setAmount(account.getWallet().getAmount() + priceReturn);
                    booking.get().getCustomer().getWallet().setTransactions(transactionService.AddTransaction(
                            account.getWallet().getTransactions(), account.getWallet(), priceReturn, TransactionType.BOOKING_REJECT
                    ));
                    transactionRepository.saveAll(account.getWallet().getTransactions());
                    walletRepository.save(account.getWallet());
                    authenticationRepository.save(account);
                    bookingDetailRepository.save(detail);

                    Account owner = booking.get().getLocation().getOwner();
                    owner.getWallet().setAmount(owner.getWallet().getAmount() - priceReturn);
                    owner.getWallet().setTransactions(transactionService.AddTransaction(
                            owner.getWallet().getTransactions(), owner.getWallet(), priceReturn, TransactionType.WITHDRAW_REJECT
                    ));
                    bookingRepository.save(booking.get());
                    transactionRepository.saveAll(owner.getWallet().getTransactions());
                    walletRepository.save(owner.getWallet());
                    authenticationRepository.save(owner);
                    return booking.get();
                }
            }
        }
        return null;
    }


    public List<Booking> getBookingsByOwner(long id) {
        Location location = locationRepository.findByOwnerId(id);
        return bookingRepository.findByLocationId(location.getId());
    }

    public List<Booking> getAllBookingSuccess(){
        return bookingRepository.findAll();
    }
    public int getSizeBooking(){
        return bookingRepository.findAll().size();
    }

    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }


    public boolean checkCode(long id, String code) {
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isPresent()) {
            booking.get().setStatus(BookingStatus.SUCCESS);
            bookingRepository.save(booking.get());
            return true;
        }
        return false;
    }


}




