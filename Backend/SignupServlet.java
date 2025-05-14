import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
        }
        UserRequest userRequest = gson.fromJson(sb.toString(), UserRequest.class);

        JsonObject jsonResponse = new JsonObject();
        if (userRequest == null || userRequest.username == null || userRequest.email == null || userRequest.password == null) {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Invalid input data");
            resp.getWriter().write(jsonResponse.toString());
            return;
        }

        try {
            if (userDAO.isUsernameExists(userRequest.username)) {
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "Username already exists");
            } else {
                boolean created = userDAO.createUser(userRequest.username, userRequest.email, userRequest.password);
                jsonResponse.addProperty("success", created);
                jsonResponse.addProperty("message", created ? "Signup successful" : "Signup failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Database error");
        }
        resp.getWriter().write(jsonResponse.toString());
    }

    private static class UserRequest {
        String username;
        String email;
        String password;
    }
}
