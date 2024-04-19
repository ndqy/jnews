<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!-- ======= Post Grid Section ======= -->
<% 
	
	//Lấy cấu trúc hiển thị trong phiên
	ArrayList<String> postGrid = (ArrayList<String>)session.getAttribute("postGrid");


%>
    <section id="posts" class="posts">
      <div class="container" data-aos="fade-up">
        <div class="row g-5">
          <div class="col-lg-4">
            <%
            //Vị trí đầu tiên
            if(postGrid !=null && postGrid.size()>0){
            	out.append(postGrid.get(0));
            }
            
            %>

          </div>

          <div class="col-lg-8">
            <div class="row g-5">
              
              <%
              	// 2 cột
              	if(postGrid !=null && postGrid.size()>0){
            	out.append(postGrid.get(1));
            }
              %>

            </div>
          </div>

        </div> <!-- End .row -->
      </div>
    </section> <!-- End Post Grid Section -->