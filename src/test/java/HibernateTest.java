import com.smalko.weather.weather.entity.Location;
import com.smalko.weather.weather.entity.Users;
import com.smalko.weather.weather.util.HibernateUtil;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

public class HibernateTest {

    @Test
    void addLocationToUser() {
        try (var sessionFactory = HibernateUtil.getSessionFactory();
             var session = sessionFactory.getCurrentSession()){

            session.beginTransaction();

            var users = Users.builder()
                    .username("SMalko")
                    .password("123")
                    .build();

            users.addLocation(Location.builder()
                    .name("Ostrow")
                    .longitude(1.22)
                    .latitude(2.33)
                    .build());


            session.merge(users);

            session.getTransaction().commit();

        }
    }
}
