package burgerput.project.zenput.web.error;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

import static burgerput.project.zenput.Const.BURGERPUTSITE;

@Slf4j
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @GetMapping("/*")
    public void error500(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object attribute = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        log.info("============Error Controller =============");
        log.info("Error page info ={}", request.getRequestURI());
        log.info("Error Controller ={}", attribute.toString());

        response.sendRedirect(BURGERPUTSITE);
    }

}
