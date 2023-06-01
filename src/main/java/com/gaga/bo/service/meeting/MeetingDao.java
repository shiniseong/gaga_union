package com.gaga.bo.service.meeting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gaga.bo.service.domain.Filter;
import com.gaga.bo.service.domain.Meeting;
import com.gaga.bo.service.domain.MeetingReview;

@Mapper
public interface MeetingDao {

	public Meeting getMeeting(int meetingNo) throws Exception;
	
	public List<Meeting> getMeetingListFromParentClubNo(int clubNo) throws Exception;
	
	public List<Meeting> getMyMeetingList(int userNo) throws Exception;
	
	public List<Meeting> getMeetingListInChat(int userNo) throws Exception;
	
	public List<Meeting> getMeetingList(Filter filter) throws Exception;
	
	public List<Meeting> getMeetingListByKeyword(Map<String, String> map) throws Exception;
	
	public void addMeeting(Meeting meeting) throws Exception;
	
	public void updateMeeting(Meeting meeting) throws Exception;
	
	public void updateMeetingSuccess(Meeting meeting) throws Exception;
	
	public void deleteMeeting(int meetingNo) throws Exception;
	
	public void addMeetingMember(Map<String, String> map) throws Exception;
	
	public void updateMember(Map<String, String> map) throws Exception;
	
	public void deleteMeetingMember(Map<String, String> map) throws Exception;

	

	
	//미팅 리뷰
	public void addMeetingReview(MeetingReview meetingReview) throws Exception;
	
	public MeetingReview getMeetingReview(int meetingReviewNo) throws Exception;
	
	public void updateMeetingReview(MeetingReview meetingReview) throws Exception;
	
	public List<MeetingReview> getMeetingReviewList(int meetinNo) throws Exception;
	
	public void deleteMeetingReview(int meetingReviewNo) throws Exception;
	
	
	//카테고리 관련
	public List<HashMap<Integer, String>> getMainCategory() throws Exception;
	
	public List<HashMap<Integer, String>> getSubCategory() throws Exception;


		
}
