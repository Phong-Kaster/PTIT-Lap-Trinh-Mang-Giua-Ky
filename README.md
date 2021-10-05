<h1 align="center"> Lập Trình Mạng <br/>
 Đề tài: 16
<h1>


# [**Table Of Content**](#table-of-content)
- [**Table Of Content**](#table-of-content)
- [**Introduce**](#introduce)
- [**Topic**](#topic)
- [**How to run**](#how-to-run)
- [**Post Script**](#post-script)

# [**Introduce**](#introduce)
 Chào các bạn, mình tên là Nguyễn Thành Phong, lớp D18CQCN03-N, khóa 2018-2023. Đây là phần đồ án của mình với môn Lập Trình Mạng do thầy Phan Thanh Hy giảng dạy. Đồ án này là bài thi giữa kỳ của tụi mình. Hi vọng sẽ giúp ích cho các bạn

# [**Topic**](#topic)

	Dưới đây là chi tiết đề tài của nhóm chúng mình nè:
	
	Xây chương trình giao diện socket client – server bằng java với giao thức UDP
	

>Client:
-	Nhập vào tên địa chỉ, cổng kết nối với server, nếu không thành công thì thông báo nhập lại còn thành công thì kết nối.
-	Sau đó vào giao diện cho phép nhập tên, cổng, username và password của sql gửi lên server để thực hiện kết nối csdl.
-	Nhập thông tin gồm họ tên, mã sinh viên, sdt gửi lên server.
-	Sau đó vào làm bài thi, 1 bài thi sẽ có 10 câu, mỗi câu chỉ 30 giây.
-	Hiển thị kết quả đã làm bài thi.


>Server:
-	Nhận thông tin sql từ client và kết nối sql server.
-	Nhận thông tin người dùng để lấy câu hỏi gửi về cho client thi.
-	Sau khi kết thúc 10 câu hỏi thì sẽ server sẽ tính kết quả của người thi gửi về cho client.
 	Chú ý: csdl sinh viên tự tạo.

Note: cơ sở dữ liệu sinh viên tự tạo.

# [**How to run**](#how-to-run)

	Để chạy được chương trình này, các bạn cần phải có SQL Server. Mở nó lên và tạo một cơ sở dữ liệu có tên `LTM_GK` và chép nội dung của tệp tin script để tạo cơ sở dữ liệu.
	
	Những thông tin khác như cổng, tài khoản để tham gia thì chúng mình đã lưu mặc định lại không cần thay đổi gì.
	
	Mở SQL Server Configuration Manager -> SQL Server Network Configuration -> Protocol for SERVER. Đảm bảo TCP/IP đã được bật nhé.
	
# [**Post Script**](#post-script)
	
	Do đây là đề tài giữa kì chỉ xoay quanh kiến thức về TCP hoặc UDP nên khá đơn giản. Hy vọng phần bài làm của nhóm chúng mình sẽ có ích cho các bạn đang đọc bài viết này.