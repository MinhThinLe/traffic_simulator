- Để một lớp được coi là một phương tiện, nó cần phải thực thi interface
  `Vehicle`, việc này sẽ:
    - Đáp ứng tiêu chí dễ dàng tái sử dụng code điều tiết giao thông
    - Dễ dàng thêm các loại phương tiện mới

Ví dụ định nghĩa interface Vehicle
    public Vertex nextVertex();         // Trả về đỉnh tiếp theo mà phương tiện này muốn đi tới
    public Vertex nextNextVertex();     // Trả về đỉnh tiếp theo nữa mà phương tiện
                                        // này muốn đi tới (đọc roads.md để biết thông tin chi tiết)
    public int getVehiclePriority();    // Trả về độ ưu tiên của phương tiện
    public void draw();                 // Vẽ phương tiện này trên bản đồ
