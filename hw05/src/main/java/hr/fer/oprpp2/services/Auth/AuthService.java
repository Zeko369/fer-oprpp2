package hr.fer.oprpp2.services.Auth;

import hr.fer.oprpp2.dao.DAOProvider;
import hr.fer.oprpp2.model.BlogUser;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class AuthService {

    public LoginError login(String username, String password) {
        Optional<BlogUser> user = DAOProvider.getDAO().userDao().getUserByUsername(username);
        if (user.isEmpty()) {
            return LoginError.USER_NOT_FOUND;
        }

        System.out.println(password);

        try {
            String passwordHash = this.hashPassword(password);
            System.out.println(passwordHash);
            if (!user.get().getPasswordHash().equals(passwordHash)) {
                return LoginError.WRONG_PASSWORD;
            }

            return LoginError.OK;
        } catch (NoSuchAlgorithmException e) {
            return LoginError.ERROR;
        }
    }


    public RegisterError register(String username, String firstName, String lastName, String email, String password) {
        if (DAOProvider.getDAO().userDao().getUserByUsername(username).isPresent()) {
            return RegisterError.USERNAME_IN_USE;
        }

        if (DAOProvider.getDAO().userDao().getUserByEmail(email).isPresent()) {
            return RegisterError.EMAIL_IN_USE;
        }

        try {
            BlogUser user = new BlogUser().setUsername(username)
                    .setEmail(email)
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setPasswordHash(this.hashPassword(password));

            DAOProvider.getDAO().userDao().saveUser(user);
            return RegisterError.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return RegisterError.ERROR;
        }
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        StringBuilder sb = new StringBuilder();
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        for (byte b : md.digest(password.getBytes(StandardCharsets.UTF_8))) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

}
