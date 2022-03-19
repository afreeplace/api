package seyfa.afreeplace.utils.response;

import seyfa.afreeplace.exceptions.FormException;
import seyfa.afreeplace.utils.constants.ExceptionConstants;
import org.springframework.validation.BindingResult;

public class BindingResultWrapper {

    public static void checkFormErrors(BindingResult bindingResult)  {

        if(bindingResult.hasErrors()) {

            System.out.println("binding result has erros");
            bindingResult.getAllErrors().forEach(e -> {
                System.err.println(e.getCode() + "(" + e.getDefaultMessage() + ") : " + e.getDefaultMessage());
            });

            throw new FormException(ExceptionConstants.formErrors());
        }

    }
}
