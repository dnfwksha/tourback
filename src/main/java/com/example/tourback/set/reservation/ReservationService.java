package com.example.tourback.set.reservation;

import com.example.tourback.global.SecurityUtils;
import com.example.tourback.set.companion.Companion;
import com.example.tourback.set.companion.CompanionRepository;
import com.example.tourback.set.reservation.querydsl.ReservationQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.tourback.set.reservation.Reservation.PaymentStatus.PAID;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final SecurityUtils securityUtils;

    private final ReservationRepository reservationRepository;
    private final CompanionRepository companionRepository;

    public void save(ReservationRequestDto reservationRequestDto) {
//        long bookingNum = reservationRepository.count();
//        String bookingCode = "K" + bookingNum;
        String bookingCode = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssnnnnnnnnn"));

        Reservation reservation = new Reservation();
        reservation.setUsername(securityUtils.getCurrentUsername());
        reservation.setBookingCode(bookingCode);
        reservation.setProductCode(reservationRequestDto.getReservationDto().getProductCode());
        reservation.setDepartureDate(reservationRequestDto.getReservationDto().getDepartureDate());
        reservation.setArrivalDate(reservationRequestDto.getReservationDto().getArrivalDate());
        reservation.setNumberOfTravelers(reservationRequestDto.getReservationDto().getNumberOfTravelers());
        reservation.setTotalCost(reservationRequestDto.getReservationDto().getTotalCost());
        reservation.setPaymentType(reservationRequestDto.getReservationDto().getPaymentType());
        reservation.setName(reservationRequestDto.getReservationDto().getReservationName());
        reservation.setPhone(reservationRequestDto.getReservationDto().getReservationPhone());
        reservation.setNote(reservationRequestDto.getReservationDto().getNote());
        reservation.setPaymentStatus(PAID);
        reservation.setBoardingLocation(reservationRequestDto.getReservationDto().getReservationBoardingLocation());
        reservationRepository.save(reservation);


        for (int i = 0; i < reservationRequestDto.getCompanionDtos().size(); i++) {
            Companion companion = new Companion();
            companion.setNumberOfPeople(reservationRequestDto.getCompanionDtos().get(i).getNumberOfPeople());
            companion.setBookingCode(bookingCode);
            companion.setName(reservationRequestDto.getCompanionDtos().get(i).getCompanionName());
            companion.setPhone(reservationRequestDto.getCompanionDtos().get(i).getCompanionPhone());
            companion.setBoardingLocation(reservationRequestDto.getCompanionDtos().get(i).getCompanionBoardingLocation());
            companionRepository.save(companion);
        }
    }

    public List<ReservationQueryDto> myOrder() {
        return reservationRepository.myOrder(securityUtils.getCurrentUsername());
    }
}
