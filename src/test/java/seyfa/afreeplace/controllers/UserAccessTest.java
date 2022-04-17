package seyfa.afreeplace.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import seyfa.afreeplace.entities.business.*;
import seyfa.afreeplace.entities.request.UserRequest;
import seyfa.afreeplace.exceptions.ManagerException;
import seyfa.afreeplace.repositories.*;
import seyfa.afreeplace.services.geocoding.GeocodingService;
import seyfa.afreeplace.utils.*;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserAccessTest {


    @Autowired
    UserRepository userRepository;

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    GeocodingService geocodingService;

    @Autowired
    DayRepository dayRepository;

    @Autowired
    HoursRepository hoursRepository;

    @Autowired
    UserRequest userRequest;

    @Autowired
    UserAccess userAccess;

    int userId, tradeId, addrId, dayId, hoursId;
    int userId2, tradeId2, addrId2, dayId2, hoursId2;
    @BeforeEach
    public void before() throws Exception {
        User user =  createUser("fallou@test.fr");
        userId = user.getId();
        tradeId = user.getTrades().get(0).getId();
        addrId = user.getTrades().get(0).getAddress().getId();
        dayId = user.getTrades().get(0).getSchedule().get(0).getId();
        hoursId = user.getTrades().get(0).getSchedule().get(0).getHours().get(0).getId();
        // "auth user"
        userRequest.setAuthUser(userRepository.findById(userId).get());

        User user2 =  createUser("fallou2@test.fr");
        userId2 = user2.getId();
        tradeId2 = user2.getTrades().get(0).getId();
        addrId2  = user2.getTrades().get(0).getAddress().getId();
        dayId2   = user2.getTrades().get(0).getSchedule().get(0).getId();
        hoursId2 = user2.getTrades().get(0).getSchedule().get(0).getHours().get(0).getId();
    }

    private User createUser(String email) throws Exception {
        User user = UserBuilderTest.create(userRepository, email, "password");

        Trade trade = TradeBuilderTest.create(tradeRepository, "name", Trade.Status.VALIDATED);
        trade.setOwner(user);
        user.getTrades().add(trade);
        userRepository.save(user);

        Address address = AddressBuilderTest.create(addressRepository, geocodingService.findByAddress("Champs elysées").get(0));
        address.setTrade(trade);
        trade.setAddress(address);

        ScheduleDay scheduleDay = DayBuilderTest.create(dayRepository);
        scheduleDay.setTrade(trade);
        trade.getSchedule().add(scheduleDay);
        tradeRepository.save(trade);

        Hours hours = new Hours();
        hours.setBegin(LocalTime.now());
        hours.setEnd(LocalTime.now());
        hours.setDay(scheduleDay);
        hoursRepository.save(hours);

        scheduleDay.getHours().add(hours);
        dayRepository.save(scheduleDay);


        return user;
    }

    @AfterEach
    public void after() {
        UserBuilderTest.delete(userId, userRepository);
        UserBuilderTest.delete(userId2, userRepository);
    }

    @Test
    public void ok() {
        assertNotNull(userAccess);
    }

    @Test
    public void testUserAccessWprks() {
        userAccess.hasRightToEditTrade(tradeRepository.findById(tradeId).get().getId());
    }

    @Test
    public void testUserAccessRefused() {
        assertThrows(ManagerException.class, () -> {
            userAccess.hasRightToEditTrade(tradeRepository.findById(tradeId2).get().getId());
        });
    }

    @Test
    public void testUserAccessNoUser() {
        User fakeUser = new User();
        fakeUser.setId(-1);
        userRequest.setAuthUser(fakeUser);
        assertThrows(ManagerException.class, () -> {
            userAccess.hasRightToEditTrade(tradeRepository.findById(tradeId).get().getId());
        });
    }

    @Test
    public void testUserAccessNoTrade() {
        assertThrows(ManagerException.class, () -> {
            userAccess.hasRightToEditTrade(-1);
        });
    }

    @Test
    public void testAddressAccessWorks() {
        userAccess.hasRighToEditAddress(addrId);
    }

    @Test
    public void testAddressAccessRefused() {
        assertThrows(ManagerException.class, () -> {
            userAccess.hasRighToEditAddress(addrId2);
        });
    }

    @Test
    public void testScheduleAccessWorks() {
        userAccess.hasRightToEditSchedule(dayId);
    }

    @Test
    public void testScheduleAccessRefused() {
        assertThrows(ManagerException.class, () -> {
            userAccess.hasRightToEditSchedule(dayId2);
        });
    }

    @Test
    public void testHoursAccessWorks() {
        userAccess.hasRightToEditHours(hoursId);
    }

    @Test
    public void testHoursAccessRefused() {
        assertThrows(ManagerException.class, () -> {
            userAccess.hasRightToEditHours(hoursId2);
        });
    }


}
