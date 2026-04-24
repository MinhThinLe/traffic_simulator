- Đường xe chạy là một đồ thị
- Để mô phỏng đường một chiều/đường có nhiều làn, có thể sử dụng một đồ thị có
  hướng
- Mỗi đỉnh nên cách nhau nhiều nhất 5m (có thể cho khoảng cách ngắn hơn để việc
  chờ đèn đỏ trông thực tế hơn)
- Để tránh va chạm, mỗi đỉnh chỉ nên chứa nhiều nhất 1 phương tiện
- Mỗi đỉnh có một hàng đợi ưu tiên giúp kiểm soát lượng xe vào
- Với ngã 3/4/5, hàng đợi này kết hợp với độ ưu tiên của phương tiện sẽ giúp
  việc ra/vào không gây va chạm 
- Việc sử dụng đồ thị cũng làm cho việc tìm đường dễ dàng hơn với các thuật
  toán tìm đường trên đồ thị (A*/Dijkstra)
- Sau khi phương tiện đã dành đủ thời gian cần chờ ở 1 đỉnh (mô phỏng tốc độ).
  Đỉnh hiện tại sẽ giữ trách nhiệm kiểm tra xem phương tiện có thể vào được
  đỉnh tiếp theo hay không. Sau khi đã xác nhận chuyển giao phụ trách phương
  tiện cho đỉnh tiếp theo, đỉnh hiện tại sẽ xóa phương tiện hiện tại và nhận
  phương tiện mới vào từ hàng đợi. 

Ví dụ định nghĩa lớp Đỉnh
```java
class Road {
    private PriorityQueue<Vehicle> queue;           // Hàng đợi ưu tiên chứa danh
                                                    // sách các phương tiện muốn
                                                    // vào đỉnh này
    private Vehicle vehicle;                        // Phương tiện đang ở trong
                                                    // đỉnh này 
    private Optional<TrafficLight> trafficLight;    // Đèn giao thông, quyết định 
                                                    // xe có thể đi vào đỉnh này hay không
}
```

- Với các phương tiện phóng nhanh/ưu tiên, đơn giản là tăng chỉ số ưu tiên của
  phương tiện đó trong hàng đợi ưu tiên. 
- Các phương tiện nên có phương thức trả về đỉnh tiếp theo và đỉnh sau đó (tiếp
  theo của tiếp theo) mà phương tiện muốn tới để có thể gán thứ tự ưu tiên cho
  các phương tiện. Ví dụ: xe rẽ phải ở ngã tư nên được ưu tiên hơn xe rẽ trái.
- Các ngã rẽ nên được biểu diễn trọn vẹn dưới dạng 1 file để dễ dàng thay
  đổi/nạp bản đồ mới, đáp ứng tiêu chí "Tính tái sử dụng"
