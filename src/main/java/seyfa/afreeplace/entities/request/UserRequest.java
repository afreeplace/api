package seyfa.afreeplace.entities.request;


import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import seyfa.afreeplace.entities.business.User;

@Component
@RequestScope
public class UserRequest {

    private static final long serialVersionUID = 1L;

    public static int requestId = 1;

    User user;

    public UserRequest() {

    }

    public User getAuthUser() {
        return user;
    }

    public void setAuthUser(User authUser) {
        user = authUser;
        requestId++;
    }

}
