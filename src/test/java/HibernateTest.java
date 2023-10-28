import com.smalko.weather.weather.entity.Location;
import com.smalko.weather.weather.user.UsersEntity;
import com.smalko.weather.weather.util.HibernateUtil;
import org.junit.jupiter.api.Test;

public class HibernateTest {

    @Test
    void addLocationToUser() {
        try (var sessionFactory = HibernateUtil.getSessionFactory();
             var session = sessionFactory.getCurrentSession()){

            session.beginTransaction();

            var users = UsersEntity.builder()
                    .username("SMalko")
                    .password("123")
                    .build();

            users.addLocation(Location.builder()
                    .name("Ostrow")
                    .longitude(1.22)
                    .latitude(2.33)
                    .build());

            users.addLocation(Location.builder()
                    .name("Kiev")
                    .longitude(1.44)
                    .latitude(4.22)
                    .build());

            session.merge(users);

            session.getTransaction().commit();

        }
    }
}
