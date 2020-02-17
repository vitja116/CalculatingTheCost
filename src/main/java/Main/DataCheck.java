package Main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class DataCheck {

    public boolean checkDescription(String desc) {
        return desc != null;
    }

    public boolean checkStartDateAndEndDate(List<String> data) {
        if (data.size() != 2) {
            return false;
        } else {
            for (String value : data) {
                if (value.trim().equals("")) {
                    return true;
                }
                /* Date is not 'null' */
                else {
                    SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
                    sdfrmt.setLenient(false);
                    try {
                        sdfrmt.parse(value);
                    } catch (ParseException e) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean checkTaskInfo(List<String> taskInfo) {
        if (taskInfo.size() != 4) {
            return false;
        }
        for (String value : taskInfo) {
            if (value == null) {
                return false;
            }
        }

        return true;
    }

    public boolean checkCloseTaskInfo(List<String> taskInfo) {
        if (taskInfo.size() != 2) {
            return false;
        }
        for (String value : taskInfo) {
            if (value == null) {
                return false;
            }
        }

        return true;
    }

}
