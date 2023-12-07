# The Weather Project

This project was made according to the [technical assignment](https://zhukovsd.github.io/java-backend-learning-course/Projects/WeatherViewer/), which was created as a mentoring program from the [Sergey Zhukov](https://www.linkedin.com/in/zhukovsd/).

Web application for viewing the current weather. The user can register and add one or more locations (cities, villages, other points) to the collection, after which the main page of the application starts displaying the list of locations with their current weather.

When a user authorizes, the backend application creates a session with an ID and sets this ID in HTTP response cookies. The session contains the ID of the authorized user.

Further, at each request to any page, the backend application analyzes the cookies from the request and determines whether there is a session for the ID from the cookies. If there is, the page is rendered for the user whose ID matches the session ID from the cookies.

During authorization, the session is also written to the database:
- **id**
- **UserId** - User for whom the session is created.
- **Datetime** - Session expiration time. Equal to the time of session creation plus 24 hours.

And at the next login to the application there is a check through **AuthorizationFilter** and compare the session from the cookie and with the one in the database and if they match, a user session is created.
Sessions that have expired are deleted by **SessionCleanupService** from the database. It checks for expired sessions once an hour.

[OpenWeather API](https://openweathermap.org) was used to display weather or search for cities by name.

An authorized user can add to the list of locations he wants to follow. The user can remove a location from the list.

For unauthorized users who want to add a location to favorites, they will be redirected to the application login page.

## Technologies that were used to write the project:
- The backend was implemented using **javax.servlet**.
- **PostgreSQL** was used as a database.
- **Hibernate** implements the object-relational model - a technology that "connects" program entities and corresponding records in the database.
- **Thymeleaf** was chosen as the templateizer
- **Frontend** - HTML/CSS, Bootstrap
- **JUtin 5** tests

Deployment was on **AWS** using **Elastic Beanstalk**, and the **RDS** was Managed Relational Database Service. 

# [Project](http://weather.eu-central-1.elasticbeanstalk.com)


