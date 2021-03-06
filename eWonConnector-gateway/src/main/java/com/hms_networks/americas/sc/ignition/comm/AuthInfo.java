package com.hms_networks.americas.sc.ignition.comm;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.UnsupportedEncodingException;
import org.apache.commons.lang3.StringUtils;
import com.hms_networks.americas.sc.ignition.EwonConsts;

/**
 * Object containing authentication information for accessing basic Talk2M features and DataMailbox.
 *
 * @author HMS Networks, MU Americas Solution Center
 */
public class AuthInfo {
  /** Talk2M account name */
  private final String account;

  /** Talk2M account username */
  private final String username;

  /** Talk2M account password */
  private final String password;

  /** Talk2M developer ID */
  private final String devId;

  /** Talk2M token */
  private final String token;

  /** Ewon device username */
  private final String ewonUsername;

  /** Ewon device password */
  private final String ewonPassword;

  /**
   * This constructor provides the information needed to access basic Talk2M features and
   * DataMailbox.
   *
   * @param account Talk2M account name
   * @param username Talk2M account username
   * @param password Talk2M account password
   * @param devId Talk2M developer ID
   * @param token Talk2M token
   */
  public AuthInfo(String account, String username, String password, String devId, String token) {
    this.account = urlEncodeValue(account);
    this.username = urlEncodeValue(username);
    this.password = urlEncodeValue(password);
    this.devId = urlEncodeValue(devId);
    this.token = urlEncodeValue(token);
    this.ewonUsername = null;
    this.ewonPassword = null;
  }

  /**
   * This constructor includes a username and password for a specific ewon device, to use the
   * services of that device through Talk2M.
   *
   * @param account Talk2M account name
   * @param username Talk2M account username
   * @param password Talk2M account password
   * @param devId Talk2M developer ID
   * @param token Talk2M token
   * @param ewonUsername Ewon device username
   * @param ewonPassword Ewon device password
   */
  public AuthInfo(
      String account,
      String username,
      String password,
      String devId,
      String token,
      String ewonUsername,
      String ewonPassword) {
    this.account = urlEncodeValue(account);
    this.username = urlEncodeValue(username);
    this.password = urlEncodeValue(password);
    this.devId = urlEncodeValue(devId);
    this.token = urlEncodeValue(token);
    this.ewonUsername = urlEncodeValue(ewonUsername);
    this.ewonPassword = urlEncodeValue(ewonPassword);
  }

  /**
   * Get Talk2M account name
   *
   * @return Talk2M account name
   */
  public String getAccount() {
    return account;
  }

  /**
   * Get Talk2M account username
   *
   * @return Talk2M account username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Get Talk2M account password
   *
   * @return Talk2M account password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Get Talk2M developer ID
   *
   * @return Talk2M developer ID
   */
  public String getDevId() {
    return devId;
  }

  /**
   * Get Talk2M token
   *
   * @return Talk2M token
   */
  public String getToken() {
    return token;
  }

  /**
   * Get Ewon device username
   *
   * @return Ewon device username
   */
  public String getEwonUsername() {
    return ewonUsername;
  }

  /**
   * Get Ewon device password
   *
   * @return Ewon device password
   */
  public String getEwonPassword() {
    return ewonPassword;
  }

  /**
   * Generate a string with authentication information for use with the DMWeb API on Talk2M.
   *
   * @return DMWeb API authentication information string
   */
  public String toDMPostString() {
    return String.format("%s=%s&%s=%s", EwonConsts.T2M_DEVKEY, devId, EwonConsts.T2M_TOKEN, token);
  }

  /**
   * Generate a string with authentication information for use with the M2Web API on Talk2M.
   *
   * @return M2Web API authentication information string
   */
  public String toM2WPostString() {
    String ret =
        String.format(
            "%s=%s&%s=%s&%s=%s&%s=%s",
            EwonConsts.T2M_ACCOUNT,
            account,
            EwonConsts.T2M_USERNAME,
            username,
            EwonConsts.T2M_PASSWORD,
            password,
            EwonConsts.T2M_DEVKEY,
            devId);
    if (!StringUtils.isBlank(ewonUsername) && !StringUtils.isBlank(ewonPassword)) {
      ret +=
          String.format(
              "&%s=%s&%s=%s",
              EwonConsts.T2M_DEVICE_USERNAME,
              ewonUsername,
              EwonConsts.T2M_DEVICE_PASSWORD,
              ewonPassword);
    }
    return ret;
  }

  /**
   * Creates a URL safe string from the passed in value
   *
   * @param value input string to be made url safe
   * @return The URL safe string
   */
  private String urlEncodeValue(String value) {
    try {
      return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    } catch (UnsupportedEncodingException e) {
      // Return the passed in string, the string could not be encoded
      return value;
    }
  }
}
