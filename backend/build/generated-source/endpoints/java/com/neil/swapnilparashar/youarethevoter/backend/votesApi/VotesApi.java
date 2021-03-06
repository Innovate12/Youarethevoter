/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 1969-12-31 23:59:59 UTC)
 * on 2017-02-09 at 20:52:14 UTC 
 * Modify at your own risk.
 */

package com.neil.swapnilparashar.youarethevoter.backend.votesApi;

/**
 * Service definition for VotesApi (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link VotesApiRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class VotesApi extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.22.0 of the votesApi library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://youarethevoter-1384.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "votesApi/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public VotesApi(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  VotesApi(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "newVoteQuestion".
   *
   * This request holds the parameters needed by the votesApi server.  After setting any optional
   * parameters, call the {@link NewVoteQuestion#execute()} method to invoke the remote operation.
   *
   * @param content the {@link com.neil.swapnilparashar.youarethevoter.backend.votesApi.model.VoteDetails}
   * @return the request
   */
  public NewVoteQuestion newVoteQuestion(com.neil.swapnilparashar.youarethevoter.backend.votesApi.model.VoteDetails content) throws java.io.IOException {
    NewVoteQuestion result = new NewVoteQuestion(content);
    initialize(result);
    return result;
  }

  public class NewVoteQuestion extends VotesApiRequest<com.neil.swapnilparashar.youarethevoter.backend.votesApi.model.GenericBean> {

    private static final String REST_PATH = "newVoteQuestion";

    /**
     * Create a request for the method "newVoteQuestion".
     *
     * This request holds the parameters needed by the the votesApi server.  After setting any
     * optional parameters, call the {@link NewVoteQuestion#execute()} method to invoke the remote
     * operation. <p> {@link NewVoteQuestion#initialize(com.google.api.client.googleapis.services.Abst
     * ractGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param content the {@link com.neil.swapnilparashar.youarethevoter.backend.votesApi.model.VoteDetails}
     * @since 1.13
     */
    protected NewVoteQuestion(com.neil.swapnilparashar.youarethevoter.backend.votesApi.model.VoteDetails content) {
      super(VotesApi.this, "POST", REST_PATH, content, com.neil.swapnilparashar.youarethevoter.backend.votesApi.model.GenericBean.class);
    }

    @Override
    public NewVoteQuestion setAlt(java.lang.String alt) {
      return (NewVoteQuestion) super.setAlt(alt);
    }

    @Override
    public NewVoteQuestion setFields(java.lang.String fields) {
      return (NewVoteQuestion) super.setFields(fields);
    }

    @Override
    public NewVoteQuestion setKey(java.lang.String key) {
      return (NewVoteQuestion) super.setKey(key);
    }

    @Override
    public NewVoteQuestion setOauthToken(java.lang.String oauthToken) {
      return (NewVoteQuestion) super.setOauthToken(oauthToken);
    }

    @Override
    public NewVoteQuestion setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (NewVoteQuestion) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public NewVoteQuestion setQuotaUser(java.lang.String quotaUser) {
      return (NewVoteQuestion) super.setQuotaUser(quotaUser);
    }

    @Override
    public NewVoteQuestion setUserIp(java.lang.String userIp) {
      return (NewVoteQuestion) super.setUserIp(userIp);
    }

    @Override
    public NewVoteQuestion set(String parameterName, Object value) {
      return (NewVoteQuestion) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "sayHello".
   *
   * This request holds the parameters needed by the votesApi server.  After setting any optional
   * parameters, call the {@link SayHello#execute()} method to invoke the remote operation.
   *
   * @param name
   * @return the request
   */
  public SayHello sayHello(java.lang.String name) throws java.io.IOException {
    SayHello result = new SayHello(name);
    initialize(result);
    return result;
  }

  public class SayHello extends VotesApiRequest<com.neil.swapnilparashar.youarethevoter.backend.votesApi.model.MyBean> {

    private static final String REST_PATH = "sayHello";

    /**
     * Create a request for the method "sayHello".
     *
     * This request holds the parameters needed by the the votesApi server.  After setting any
     * optional parameters, call the {@link SayHello#execute()} method to invoke the remote operation.
     * <p> {@link
     * SayHello#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param name
     * @since 1.13
     */
    protected SayHello(java.lang.String name) {
      super(VotesApi.this, "POST", REST_PATH, null, com.neil.swapnilparashar.youarethevoter.backend.votesApi.model.MyBean.class);
      this.name = com.google.api.client.util.Preconditions.checkNotNull(name, "Required parameter name must be specified.");
    }

    @Override
    public SayHello setAlt(java.lang.String alt) {
      return (SayHello) super.setAlt(alt);
    }

    @Override
    public SayHello setFields(java.lang.String fields) {
      return (SayHello) super.setFields(fields);
    }

    @Override
    public SayHello setKey(java.lang.String key) {
      return (SayHello) super.setKey(key);
    }

    @Override
    public SayHello setOauthToken(java.lang.String oauthToken) {
      return (SayHello) super.setOauthToken(oauthToken);
    }

    @Override
    public SayHello setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (SayHello) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public SayHello setQuotaUser(java.lang.String quotaUser) {
      return (SayHello) super.setQuotaUser(quotaUser);
    }

    @Override
    public SayHello setUserIp(java.lang.String userIp) {
      return (SayHello) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String name;

    /**

     */
    public java.lang.String getName() {
      return name;
    }

    public SayHello setName(java.lang.String name) {
      this.name = name;
      return this;
    }

    @Override
    public SayHello set(String parameterName, Object value) {
      return (SayHello) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "voteAction".
   *
   * This request holds the parameters needed by the votesApi server.  After setting any optional
   * parameters, call the {@link VoteAction#execute()} method to invoke the remote operation.
   *
   * @param content the {@link com.neil.swapnilparashar.youarethevoter.backend.votesApi.model.VoteDetails}
   * @return the request
   */
  public VoteAction voteAction(com.neil.swapnilparashar.youarethevoter.backend.votesApi.model.VoteDetails content) throws java.io.IOException {
    VoteAction result = new VoteAction(content);
    initialize(result);
    return result;
  }

  public class VoteAction extends VotesApiRequest<com.neil.swapnilparashar.youarethevoter.backend.votesApi.model.GenericBean> {

    private static final String REST_PATH = "voteAction";

    /**
     * Create a request for the method "voteAction".
     *
     * This request holds the parameters needed by the the votesApi server.  After setting any
     * optional parameters, call the {@link VoteAction#execute()} method to invoke the remote
     * operation. <p> {@link
     * VoteAction#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link com.neil.swapnilparashar.youarethevoter.backend.votesApi.model.VoteDetails}
     * @since 1.13
     */
    protected VoteAction(com.neil.swapnilparashar.youarethevoter.backend.votesApi.model.VoteDetails content) {
      super(VotesApi.this, "POST", REST_PATH, content, com.neil.swapnilparashar.youarethevoter.backend.votesApi.model.GenericBean.class);
    }

    @Override
    public VoteAction setAlt(java.lang.String alt) {
      return (VoteAction) super.setAlt(alt);
    }

    @Override
    public VoteAction setFields(java.lang.String fields) {
      return (VoteAction) super.setFields(fields);
    }

    @Override
    public VoteAction setKey(java.lang.String key) {
      return (VoteAction) super.setKey(key);
    }

    @Override
    public VoteAction setOauthToken(java.lang.String oauthToken) {
      return (VoteAction) super.setOauthToken(oauthToken);
    }

    @Override
    public VoteAction setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (VoteAction) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public VoteAction setQuotaUser(java.lang.String quotaUser) {
      return (VoteAction) super.setQuotaUser(quotaUser);
    }

    @Override
    public VoteAction setUserIp(java.lang.String userIp) {
      return (VoteAction) super.setUserIp(userIp);
    }

    @Override
    public VoteAction set(String parameterName, Object value) {
      return (VoteAction) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "voteDetails".
   *
   * This request holds the parameters needed by the votesApi server.  After setting any optional
   * parameters, call the {@link VoteDetails#execute()} method to invoke the remote operation.
   *
   * @param userID
   * @param voteID
   * @return the request
   */
  public VoteDetails voteDetails(java.lang.Integer userID, java.lang.Integer voteID) throws java.io.IOException {
    VoteDetails result = new VoteDetails(userID, voteID);
    initialize(result);
    return result;
  }

  public class VoteDetails extends VotesApiRequest<com.neil.swapnilparashar.youarethevoter.backend.votesApi.model.VoteDetails> {

    private static final String REST_PATH = "voteDetails";

    /**
     * Create a request for the method "voteDetails".
     *
     * This request holds the parameters needed by the the votesApi server.  After setting any
     * optional parameters, call the {@link VoteDetails#execute()} method to invoke the remote
     * operation. <p> {@link
     * VoteDetails#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param userID
     * @param voteID
     * @since 1.13
     */
    protected VoteDetails(java.lang.Integer userID, java.lang.Integer voteID) {
      super(VotesApi.this, "POST", REST_PATH, null, com.neil.swapnilparashar.youarethevoter.backend.votesApi.model.VoteDetails.class);
      this.userID = com.google.api.client.util.Preconditions.checkNotNull(userID, "Required parameter userID must be specified.");
      this.voteID = com.google.api.client.util.Preconditions.checkNotNull(voteID, "Required parameter voteID must be specified.");
    }

    @Override
    public VoteDetails setAlt(java.lang.String alt) {
      return (VoteDetails) super.setAlt(alt);
    }

    @Override
    public VoteDetails setFields(java.lang.String fields) {
      return (VoteDetails) super.setFields(fields);
    }

    @Override
    public VoteDetails setKey(java.lang.String key) {
      return (VoteDetails) super.setKey(key);
    }

    @Override
    public VoteDetails setOauthToken(java.lang.String oauthToken) {
      return (VoteDetails) super.setOauthToken(oauthToken);
    }

    @Override
    public VoteDetails setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (VoteDetails) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public VoteDetails setQuotaUser(java.lang.String quotaUser) {
      return (VoteDetails) super.setQuotaUser(quotaUser);
    }

    @Override
    public VoteDetails setUserIp(java.lang.String userIp) {
      return (VoteDetails) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Integer userID;

    /**

     */
    public java.lang.Integer getUserID() {
      return userID;
    }

    public VoteDetails setUserID(java.lang.Integer userID) {
      this.userID = userID;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Integer voteID;

    /**

     */
    public java.lang.Integer getVoteID() {
      return voteID;
    }

    public VoteDetails setVoteID(java.lang.Integer voteID) {
      this.voteID = voteID;
      return this;
    }

    @Override
    public VoteDetails set(String parameterName, Object value) {
      return (VoteDetails) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "voteList".
   *
   * This request holds the parameters needed by the votesApi server.  After setting any optional
   * parameters, call the {@link VoteList#execute()} method to invoke the remote operation.
   *
   * @param active
   * @param interestGroupID
   * @return the request
   */
  public VoteList voteList(java.lang.Boolean active, java.lang.Integer interestGroupID) throws java.io.IOException {
    VoteList result = new VoteList(active, interestGroupID);
    initialize(result);
    return result;
  }

  public class VoteList extends VotesApiRequest<com.neil.swapnilparashar.youarethevoter.backend.votesApi.model.VoteDetailsCollection> {

    private static final String REST_PATH = "voteList";

    /**
     * Create a request for the method "voteList".
     *
     * This request holds the parameters needed by the the votesApi server.  After setting any
     * optional parameters, call the {@link VoteList#execute()} method to invoke the remote operation.
     * <p> {@link
     * VoteList#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param active
     * @param interestGroupID
     * @since 1.13
     */
    protected VoteList(java.lang.Boolean active, java.lang.Integer interestGroupID) {
      super(VotesApi.this, "POST", REST_PATH, null, com.neil.swapnilparashar.youarethevoter.backend.votesApi.model.VoteDetailsCollection.class);
      this.active = com.google.api.client.util.Preconditions.checkNotNull(active, "Required parameter active must be specified.");
      this.interestGroupID = com.google.api.client.util.Preconditions.checkNotNull(interestGroupID, "Required parameter interestGroupID must be specified.");
    }

    @Override
    public VoteList setAlt(java.lang.String alt) {
      return (VoteList) super.setAlt(alt);
    }

    @Override
    public VoteList setFields(java.lang.String fields) {
      return (VoteList) super.setFields(fields);
    }

    @Override
    public VoteList setKey(java.lang.String key) {
      return (VoteList) super.setKey(key);
    }

    @Override
    public VoteList setOauthToken(java.lang.String oauthToken) {
      return (VoteList) super.setOauthToken(oauthToken);
    }

    @Override
    public VoteList setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (VoteList) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public VoteList setQuotaUser(java.lang.String quotaUser) {
      return (VoteList) super.setQuotaUser(quotaUser);
    }

    @Override
    public VoteList setUserIp(java.lang.String userIp) {
      return (VoteList) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Boolean active;

    /**

     */
    public java.lang.Boolean getActive() {
      return active;
    }

    public VoteList setActive(java.lang.Boolean active) {
      this.active = active;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Integer interestGroupID;

    /**

     */
    public java.lang.Integer getInterestGroupID() {
      return interestGroupID;
    }

    public VoteList setInterestGroupID(java.lang.Integer interestGroupID) {
      this.interestGroupID = interestGroupID;
      return this;
    }

    @Override
    public VoteList set(String parameterName, Object value) {
      return (VoteList) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link VotesApi}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link VotesApi}. */
    @Override
    public VotesApi build() {
      return new VotesApi(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link VotesApiRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setVotesApiRequestInitializer(
        VotesApiRequestInitializer votesapiRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(votesapiRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
