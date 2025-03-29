package com.example.tourback.set.reservation;

import com.example.tourback.global.SecurityUtils;
import com.example.tourback.set.companion.Companion;
import com.example.tourback.set.companion.CompanionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final SecurityUtils securityUtils;

    private final ReservationRepository reservationRepository;
    private final CompanionRepository companionRepository;

    public void save(ReservationRequestDto reservationRequestDto) {
        long bookingNum = reservationRepository.count();
        String bookingCode = "K" + bookingNum;

        Reservation reservation = new Reservation();
        reservation.setUsername(securityUtils.getCurrentUsername());
        reservation.setBookingCode(bookingCode);
        reservation.setDepartureDate(reservationRequestDto.getReservationDto().getDepartureDate());
        reservation.setArrivalDate(reservationRequestDto.getReservationDto().getArrivalDate());
        reservation.setNumberOfTravelers(reservationRequestDto.getReservationDto().getNumberOfTravelers());
        reservation.setTotalCost(reservationRequestDto.getReservationDto().getTotalCost());
        reservation.setPaymentType(reservationRequestDto.getReservationDto().getPaymentType());
        reservation.setSpecialRequests(reservationRequestDto.getReservationDto().getSpecialRequests());
        reservation.setName(reservationRequestDto.getReservationDto().getReservationName());
        reservation.setPhone(reservationRequestDto.getReservationDto().getReservationPhone());
        reservation.setNote(reservationRequestDto.getReservationDto().getNote());
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
}
