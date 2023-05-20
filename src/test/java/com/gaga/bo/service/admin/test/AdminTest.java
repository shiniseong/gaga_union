package com.gaga.bo.service.admin.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import com.gaga.bo.service.admin.AdminService;
import com.gaga.bo.service.domain.NoticePost;
import com.gaga.bo.service.domain.User;

@SpringBootTest
class AdminTest {

	///field
	@Autowired
	@Qualifier("adminServiceImpl")
	AdminService adminService;
	
	
	//@Test
	public void testAddNoticePost() throws Exception {
		NoticePost noticePost = new NoticePost();
		
	    noticePost.setNoticePostNo(999);
	    noticePost.setNoticePostCategoryNo(1);
	    noticePost.setNoticePostTitle("junit테스트1");
	    noticePost.setNoticePostText("junit테스트");
	    noticePost.setNoticePostImg("asdasdsad.jpg");
	    noticePost.setNoticePostRegDate(LocalDateTime.now());
	    noticePost.setUserNo(5);
	    		
	    adminService.addNoticePost(noticePost);
	  
	}
	
	//@Test
	public void testListNoticePost() throws Exception {
		List<NoticePost> noticePost = adminService.listNoticePost();
		// Verify if the list is not empty
		assertTrue(noticePost.size() > 0);
	}
	
	//@Test
	public void testGetNoticePost() throws Exception {
		// Test the getNoticePost method with an existing post number
		NoticePost noticePost = adminService.getNoticePost(3);

		// Verify the returned NoticePost
		//assertNotNull(noticePost);
		assertEquals(3, noticePost.getNoticePostNo());
	}
	//@Test
	public void testUpdateNoticePost() throws Exception {
		NoticePost noticePost = adminService.getNoticePost(3);

		// Update the NoticePost
		noticePost.setNoticePostTitle("Updated Title업데이트 업업");
		adminService.updateNoticePost(noticePost);

		// Verify the updated NoticePost
		NoticePost updatedNoticePost = adminService.getNoticePost(3);
		assertEquals("Updated Title업데이트 업업", updatedNoticePost.getNoticePostTitle());
	}
	//@Test
	public void testDeleteNoticePost() throws Exception {
		// Delete an existing NoticePost
		adminService.deleteNoticePost(3);

		// Verify if the post was deleted
		NoticePost deletedNoticePost = adminService.getNoticePost(3);
		assertNull(deletedNoticePost);
	}
	
	////////@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 블랙리스트부분
	
	//@Test
    public void testAddBlackList() throws Exception {
        int userNo = 6;

        // Add user to the blacklist
        adminService.addBlackList(userNo);

        // Retrieve the user from the database
        User user = adminService.getBlackList(userNo);
        
        assertEquals(1, user.getBlacklist());
    }

//    @Test
    public void testListBlackList() throws Exception {
        // Retrieve the list of blacklisted users
        List<User> blacklistedUsers = adminService.listBlackList();

        // Verify if the list is not empty
        assertTrue(blacklistedUsers.size() > 0);
    }

//  @Test
    public void testGetBlackList() throws Exception {
        int userNo = 6;

        // Get the user's blacklist status
        User blacklistStatus = adminService.getBlackList(userNo);

        // Verify if the user is blacklisted
        assertEquals(1, blacklistStatus.getBlacklist());
    }

	
}
