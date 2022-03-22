package seyfa.afreeplace.entities.request;


import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class UserRequest {

    private static final long serialVersionUID = 1L;

    public static int requestId = 1;

    public UserRequest() {

    }

    public Object getAuthUser() {
        return null;
    }

    public void setAuthUser(Object authUser) {
        requestId++;
    }

}
