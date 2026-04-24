- Trong dạng biểu diễn văn bản của đồ thị, mỗi đỉnh có một trường đánh dấu nó
  là đỉnh nguồn hoặc đỉnh đích.
- Lớp phụ trách đọc đồ thị có trách nhiệm lưu giá trị này sau đó gửi nó đến một
  lớp Singleton khác phụ trách sinh phương tiện.
- Lớp Singleton có trách nhiệm định kỳ sinh phương tiện (với tham số có thể tùy
  biến) và đồng thời cũng có trách nhiệm tìm đường phù hợp cho các phương tiện
  di chuyển từ một đỉnh nguồn đến một đỉnh đích bất kỳ.

Ví dụ định nghĩa lớp
```java
class VehicleSpawner {
    private ArrayList<Road> sources;            // Danh sách các đỉnh nguồn
    private ArrayList<Road> sinks;              // Danh sách các đỉnh đích
    private ArrayList<Vehicle> vehicleTypes;    // Danh sách các loại phương tiện có thể sinh
    private float timer;                        // Bộ đếm giờ để điều khiển lưu lượng phương tiện qua lại
    private MutableGraph<Road>                  // Mạng lưới đường đi

    public VehicleSpawner();                    
    public void addVehicleType(Class<? extends Vehicle> vehicleType);
    public void tick(float deltaTime);
}
```

`VehicleSpawner` cần là một thành viên của `RoadNetwork` để có thể truy cập và
