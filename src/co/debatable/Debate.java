package co.debatable;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Debate {

	@PrimaryKey
	private String id;
	private String createdBy;
	private String sessionId;
	private String debateName;
	private String debateFormat;
	private String prop1SpeakerFirstName;
	private String prop1SpeakerLastName;
	private String prop1Email;
	private String prop1Id;
	private String prop1Code;
	private String prop1Token;
	private String prop2SpeakerFirstName;
	private String prop2SpeakerLastName;
	private String prop2Email;
	private String prop2Id;
	private String prop2Code;
	private String prop2Token;
	private String opp1SpeakerFirstName;
	private String opp1SpeakerLastName;
	private String opp1Email;
	private String opp1Id;
	private String opp1Code;
	private String opp1Token;
	private String opp2SpeakerFirstName;
	private String opp2SpeakerLastName;
	private String opp2Email;
	private String opp2Id;
	private String opp2Code;
	private String opp2Token;
	private String judgeSpeakerFirstName;
	private String judgeSpeakerLastName;
	private String judgeEmail;
	private String judgeId;
	private String judgeCode;
	private String judgeToken;
	private String timerFormat;
	private String guestCode;
	private String guestToken;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getDebateName() {
		return debateName;
	}
	public void setDebateName(String debateName) {
		this.debateName = debateName;
	}
	public String getDebateFormat() {
		return debateFormat;
	}
	public void setDebateFormat(String debateFormat) {
		this.debateFormat = debateFormat;
	}
	public String getProp1SpeakerFirstName() {
		return prop1SpeakerFirstName;
	}
	public void setProp1SpeakerFirstName(String prop1SpeakerFirstName) {
		this.prop1SpeakerFirstName = prop1SpeakerFirstName;
	}
	public String getProp1SpeakerLastName() {
		return prop1SpeakerLastName;
	}
	public void setProp1SpeakerLastName(String prop1SpeakerLastName) {
		this.prop1SpeakerLastName = prop1SpeakerLastName;
	}
	public String getProp1Email() {
		return prop1Email;
	}
	public void setProp1Email(String prop1Email) {
		this.prop1Email = prop1Email;
	}
	public String getProp1Id() {
		return prop1Id;
	}
	public void setProp1Id(String prop1Id) {
		this.prop1Id = prop1Id;
	}
	public String getProp1Code() {
		return prop1Code;
	}
	public void setProp1Code(String prop1Code) {
		this.prop1Code = prop1Code;
	}
	public String getProp1Token() {
		return prop1Token;
	}
	public void setProp1Token(String prop1Token) {
		this.prop1Token = prop1Token;
	}
	public String getProp2SpeakerFirstName() {
		return prop2SpeakerFirstName;
	}
	public void setProp2SpeakerFirstName(String prop2SpeakerFirstName) {
		this.prop2SpeakerFirstName = prop2SpeakerFirstName;
	}
	public String getProp2SpeakerLastName() {
		return prop2SpeakerLastName;
	}
	public void setProp2SpeakerLastName(String prop2SpeakerLastName) {
		this.prop2SpeakerLastName = prop2SpeakerLastName;
	}
	public String getProp2Email() {
		return prop2Email;
	}
	public void setProp2Email(String prop2Email) {
		this.prop2Email = prop2Email;
	}
	public String getProp2Id() {
		return prop2Id;
	}
	public void setProp2Id(String prop2Id) {
		this.prop2Id = prop2Id;
	}
	public String getProp2Code() {
		return prop2Code;
	}
	public void setProp2Code(String prop2Code) {
		this.prop2Code = prop2Code;
	}
	public String getProp2Token() {
		return prop2Token;
	}
	public void setProp2Token(String prop2Token) {
		this.prop2Token = prop2Token;
	}
	public String getOpp1SpeakerFirstName() {
		return opp1SpeakerFirstName;
	}
	public void setOpp1SpeakerFirstName(String opp1SpeakerFirstName) {
		this.opp1SpeakerFirstName = opp1SpeakerFirstName;
	}
	public String getOpp1SpeakerLastName() {
		return opp1SpeakerLastName;
	}
	public void setOpp1SpeakerLastName(String opp1SpeakerLastName) {
		this.opp1SpeakerLastName = opp1SpeakerLastName;
	}
	public String getOpp1Email() {
		return opp1Email;
	}
	public void setOpp1Email(String opp1Email) {
		this.opp1Email = opp1Email;
	}
	public String getOpp1Id() {
		return opp1Id;
	}
	public void setOpp1Id(String opp1Id) {
		this.opp1Id = opp1Id;
	}
	public String getOpp1Code() {
		return opp1Code;
	}
	public void setOpp1Code(String opp1Code) {
		this.opp1Code = opp1Code;
	}
	public String getOpp1Token() {
		return opp1Token;
	}
	public void setOpp1Token(String opp1Token) {
		this.opp1Token = opp1Token;
	}
	public String getOpp2SpeakerFirstName() {
		return opp2SpeakerFirstName;
	}
	public void setOpp2SpeakerFirstName(String opp2SpeakerFirstName) {
		this.opp2SpeakerFirstName = opp2SpeakerFirstName;
	}
	public String getOpp2SpeakerLastName() {
		return opp2SpeakerLastName;
	}
	public void setOpp2SpeakerLastName(String opp2SpeakerLastName) {
		this.opp2SpeakerLastName = opp2SpeakerLastName;
	}
	public String getOpp2Email() {
		return opp2Email;
	}
	public void setOpp2Email(String opp2Email) {
		this.opp2Email = opp2Email;
	}
	public String getOpp2Id() {
		return opp2Id;
	}
	public void setOpp2Id(String opp2Id) {
		this.opp2Id = opp2Id;
	}
	public String getOpp2Code() {
		return opp2Code;
	}
	public void setOpp2Code(String opp2Code) {
		this.opp2Code = opp2Code;
	}
	public String getOpp2Token() {
		return opp2Token;
	}
	public void setOpp2Token(String opp2Token) {
		this.opp2Token = opp2Token;
	}
	public String getJudgeSpeakerFirstName() {
		return judgeSpeakerFirstName;
	}
	public void setJudgeSpeakerFirstName(String judgeSpeakerFirstName) {
		this.judgeSpeakerFirstName = judgeSpeakerFirstName;
	}
	public String getJudgeSpeakerLastName() {
		return judgeSpeakerLastName;
	}
	public void setJudgeSpeakerLastName(String judgeSpeakerLastName) {
		this.judgeSpeakerLastName = judgeSpeakerLastName;
	}
	public String getJudgeEmail() {
		return judgeEmail;
	}
	public void setJudgeEmail(String judgeEmail) {
		this.judgeEmail = judgeEmail;
	}
	public String getJudgeId() {
		return judgeId;
	}
	public void setJudgeId(String judgeId) {
		this.judgeId = judgeId;
	}
	public String getJudgeCode() {
		return judgeCode;
	}
	public void setJudgeCode(String judgeCode) {
		this.judgeCode = judgeCode;
	}
	public String getJudgeToken() {
		return judgeToken;
	}
	public void setJudgeToken(String judgeToken) {
		this.judgeToken = judgeToken;
	}
	public String getTimerFormat() {
		return timerFormat;
	}
	public void setTimerFormat(String timerFormat) {
		this.timerFormat = timerFormat;
	}
	public String getGuestCode() {
		return guestCode;
	}
	public void setGuestCode(String guestCode) {
		this.guestCode = guestCode;
	}
	public String getGuestToken() {
		return guestToken;
	}
	public void setGuestToken(String guestToken) {
		this.guestToken = guestToken;
	}
	
}
