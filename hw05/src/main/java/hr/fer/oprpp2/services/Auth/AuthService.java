package hr.fer.oprpp2.services.Auth;

import hr.fer.oprpp2.dao.DAOProvider;
import hr.fer.oprpp2.model.BlogUser;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class AuthService {

    public AuthResult<LoginError> login(String username, String password) {
        Optional<BlogUser> user = DAOProvider.getDAO().userDao().getUserByUsername(username);
        if (user.isEmpty()) {
            return new AuthResult<>(LoginError.USER_NOT_FOUND, null);
        }

        System.out.println(password);

        try {
            String passwordHash = this.hashPassword(password);
            System.out.println(passwordHash);
            if (!user.get().getPasswordHash().equals(passwordHash)) {
                return new AuthResult<>(LoginError.WRONG_PASSWORD, null);
            }

            return new AuthResult<>(LoginError.OK, user.get().getId());
        } catch (NoSuchAlgorithmException e) {
            return new AuthResult<>(LoginError.ERROR, null);
        }
    }


    public AuthResult<RegisterError> register(String username, String firstName, String lastName, String email, String password) {
        if (DAOProvider.getDAO().userDao().getUserByUsername(username).isPresent()) {
            return new AuthResult<>(RegisterError.USERNAME_IN_USE, null);
        }

        if (DAOProvider.getDAO().userDao().getUserByEmail(email).isPresent()) {
            return new AuthResult<>(RegisterError.EMAIL_IN_USE, null);
        }

        try {
            BlogUser user = new BlogUser().setUsername(username)
                    .setEmail(email)
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setPasswordHash(this.hashPassword(password));

            DAOProvider.getDAO().userDao().saveUser(user);
            return new AuthResult<>(RegisterError.OK, user.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return new AuthResult<>(RegisterError.ERROR, null);
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
