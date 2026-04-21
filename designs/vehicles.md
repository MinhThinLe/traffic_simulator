- Để một lớp được coi là một phương tiện, nó cần phải là lớp con kế thừa từ lớp
  trừu tượng cha là Vehicle, việc này giúp đảm bảo
    - Đáp ứng tiêu chí dễ dàng tái sử dụng code điều tiết giao thông
    - Dễ dàng thêm các loại phương tiện mới

Ví dụ định nghĩa lớp trừu tượng Vehicle
    private ArrayList<Vertex> path;     // Đường đi của phương tiện
    private Vector2 position;           // Vị trí của phương tiện ở trên màn hình
    private DrivingMode drivingMode;    // "Phong cách" lái xe của tài xế
    public Vertex nextVertex();         // Trả về đỉnh tiếp theo mà phương tiện này muốn đi tới
    public Vertex nextNextVertex();     // Trả về đỉnh tiếp theo nữa mà phương tiện
                                        // này muốn đi tới (đọc roads.md để biết thông tin chi tiết)
    public int getVehiclePriority();    // Trả về độ ưu tiên của phương tiện
    public void primitiveDraw();        // Vẽ phương tiện này bằng các hình cơ bản
    public void graphicalDraw();        // Vẽ phương tiện này với bằng texture

Ví dụ định nghĩa enum DrivingMode
    CAREFUL,                            // Tài xế lái xe cẩn thận, tốc độ thấp và sẽ có xu
                                        // hướng nhường xe khác (ưu tiên thấp)
    NORMAL,                             // Tài xế lái xe bình thường, không thay đổi tốc độ
                                        // cũng như ưu tiên.
    AGRESSIVE                           // Tài xế phóng nhanh, vượt ẩu, tốc độ cao và có tỉ
                                        // lệ vượt đèn đỏ.
