package com.github.avinashkris9.movietracker.utils;

import java.util.ArrayList;
import java.util.List;

public class APIUtils {



// making it private as it is utlity class !
  private APIUtils(){}
  public enum SHOW_TYPES {
    MOVIE,
    TV
  }
  public static final String TV_NOT_FOUND="No TV Details Found";
  public static final String NO_WATCH_LIST_ENTRY="Watch list is empty";

  public static enum API_CODES
  {
    NOT_FOUND,
    DUPLICATE,
    WATCHLIST_EMPTY

  }


}
