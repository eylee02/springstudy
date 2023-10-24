package com.gdu.staff;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gdu.staff.dao.StaffMapper;
import com.gdu.staff.dto.StaffDto;

/*
 * JUnit 처리 방법
 * 1. spring-test dependency를 추가한다.
 * 2. @RunWith를 추가한다.
 * 3. @ContextConfiguration을 추가한다.
 *    ContactDao 객체(Bean)을 생성한 방법에 따라서 아래 3가지 방식 중 선택한다.
 *    1) <bean> 태그 : @ContextConfiguration(locations="file:src/main/webapp/WEB-INF/spring/root-context.xml")
 *    2) @Bean       : @ContextConfiguration(classes=AppConfig.class)
 *    3) @Component  : @ContextConfiguration(locations="file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml")
 */

// JUnit4를 이용한다.
@RunWith(SpringJUnit4ClassRunner.class)

// ContactDao Bean 생성 방법을 알려준다.
@ContextConfiguration(locations="file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml")
public class StaffUnitTest {

  @Autowired  // Spring Container에서 ContactDao 객체(Bean)를 가져온다.
  private StaffMapper staffMapper ;  

  @Before 
  public void 삽입테스트() {
    StaffDto staff = new StaffDto("99999", "김기획", "기획부", 5000);
    int addResult = staffMapper.insertStaff(staff);
    assertEquals(1, addResult);
  }
  
  @Test  // 테스트를 수행한다.
  public void 조회테스트() {
    String sno = "99999";
    StaffDto staff = staffMapper.getStaff(sno);
    assertNotNull(staff);
  } 
 
}
