package models.validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.Employee;
import utils.DBUtil;

public class EmployeeValidator {
    public static List<String> validate(Employee e, Boolean code_duplicate_check_flag, Boolean password_check_flag) {
        List<String> errors = new ArrayList<String>();

        String code_error = _ValidateCode(e.getCode(), code_duplicate_check_flag);
        if(!code_error.equals("")) {
            errors.add(code_error);
        }

        String name_error = _ValidateName(e.getName());
        if(!name_error.equals("")) {
            errors.add(name_error);
        }

        String password_error = _ValidatePassword(e.getPassword(), password_check_flag);
        if(!password_error.equals("")) {
            errors.add(password_error);
        }

        return errors;
    }


    private static String _ValidateCode(String code, Boolean code_duplicate_check_flag) {
        if(code == null || code.equals("")) {
            return "社員番号を入力してください。";
        }

        if(code_duplicate_check_flag) {
            EntityManager em = DBUtil.createEntityManager();
            long employees_count = (long)em.createNamedQuery("checkRegisteredCode", Long.class)
                                          .setParameter("code", code)
                                          .getSingleResult();
            em.close();
            if(employees_count > 0) {
                return "入力された社員番号は既に存在しています。";
            }
        }
        return "";
    }

    private static String _ValidateName(String name) {
        if(name == null || name.equals("")) {
            return "氏名を入力してください。";
        }
        return "";
    }

    private static String _ValidatePassword(String password, Boolean password_check_flag) {
        if(password_check_flag && (password == null || password.equals(""))) {
            return "パスワードを入力してください。";
        }
        return null;
    }




}
