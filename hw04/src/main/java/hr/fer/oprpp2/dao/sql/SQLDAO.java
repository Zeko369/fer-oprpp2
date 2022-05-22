package hr.fer.oprpp2.dao.sql;

import hr.fer.oprpp2.dao.DAO;
import hr.fer.oprpp2.model.Poll;
import hr.fer.oprpp2.model.PollOption;

import java.util.List;

public class SQLDAO implements DAO {
    @Override
    public List<Poll> getPolls() {
        return null;
    }

    @Override
    public List<PollOption> getPollOptions(Long id) {
        return null;
    }

    @Override
    public void vote(Long id, Long optionId) {

    }

//    @Override
//    public List<Unos> dohvatiOsnovniPopisUnosa() throws DAOException {
//        List<Unos> unosi = new ArrayList<>();
//        Connection con = SQLConnectionProvider.getConnection();
//        PreparedStatement pst = null;
//        try {
//            pst = con.prepareStatement("select id, title from Poruke order by id");
//            try {
//                ResultSet rs = pst.executeQuery();
//                try {
//                    while (rs != null && rs.next()) {
//                        Unos unos = new Unos();
//                        unos.setId(rs.getLong(1));
//                        unos.setTitle(rs.getString(2));
//                        unosi.add(unos);
//                    }
//                } finally {
//                    try {
//                        rs.close();
//                    } catch (Exception ignorable) {
//                    }
//                }
//            } finally {
//                try {
//                    pst.close();
//                } catch (Exception ignorable) {
//                }
//            }
//        } catch (Exception ex) {
//            throw new DAOException("Pogreška prilikom dohvata liste korisnika.", ex);
//        }
//        return unosi;
//    }
//
//    @Override
//    public Unos dohvatiUnos(long id) throws DAOException {
//        Unos unos = null;
//        Connection con = SQLConnectionProvider.getConnection();
//        PreparedStatement pst = null;
//        try {
//            pst = con.prepareStatement("select id, title, message, createdOn, userEMail from Poruke where id=?");
//            pst.setLong(1, Long.valueOf(id));
//            try {
//                ResultSet rs = pst.executeQuery();
//                try {
//                    if (rs != null && rs.next()) {
//                        unos = new Unos();
//                        unos.setId(rs.getLong(1));
//                        unos.setTitle(rs.getString(2));
//                        unos.setMessage(rs.getString(3));
//                        unos.setCreatedOn(rs.getTimestamp(4));
//                        unos.setUserEMail(rs.getString(5));
//                    }
//                } finally {
//                    try {
//                        rs.close();
//                    } catch (Exception ignorable) {
//                    }
//                }
//            } finally {
//                try {
//                    pst.close();
//                } catch (Exception ignorable) {
//                }
//            }
//        } catch (Exception ex) {
//            throw new DAOException("Pogreška prilikom dohvata korisnika.", ex);
//        }
//        return unos;
//    }
}
