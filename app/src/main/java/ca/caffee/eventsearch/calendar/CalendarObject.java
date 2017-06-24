package ca.caffee.eventsearch.calendar;

import android.database.Cursor;

/**
 * Created by mtajc on 24.06.2017.
 */

public class CalendarObject {

  public int id;
  public String name;
  public String nameDisplay;
  public String accountName;
  public String ownerAccount;
  public String timezone;
  public boolean isSync;
  public boolean isVisible;
  public boolean isPrimary;
  public int color;
  public int colorKey;
  public int accessLevel;

  //ActivityConfigure
  public boolean isSelectedPersonal;

  public static CalendarObject getCalendarObject(Cursor cursor) {
    CalendarObject newOne = new CalendarObject();

    newOne.id = cursor.getInt(0);
    newOne.name = cursor.getString(1);
    newOne.nameDisplay = cursor.getString(2);
    newOne.accountName = cursor.getString(3);
    newOne.ownerAccount = cursor.getString(4);
    newOne.isVisible = cursor.getInt(5) == 1;
    newOne.isSync = cursor.getInt(6) == 1;
    newOne.color = cursor.getInt(7);
    newOne.colorKey = cursor.getInt(8);
    newOne.accessLevel = cursor.getInt(9);
    newOne.timezone = cursor.getString(10);
    newOne.isPrimary = cursor.getInt(11) != 0;
    return newOne;
  }

  @Override public String toString() {
    return "CalendarObject{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", nameDisplay='"
        + nameDisplay
        + '\''
        + ", accountName='"
        + accountName
        + '\''
        + ", ownerAccount='"
        + ownerAccount
        + '\''
        + ", timezone='"
        + timezone
        + '\''
        + ", isSync="
        + isSync
        + ", isVisible="
        + isVisible
        + ", isPrimary="
        + isPrimary
        + ", color="
        + color
        + ", colorKey="
        + colorKey
        + ", accessLevel="
        + accessLevel
        + '}';
  }
}
