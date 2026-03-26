# Project OOP: Mô phỏng giao thông

Đây là repo cho bài tập lớn của môn Lập trình hướng đối tượng về chủ đề mô
phỏng giao thông đô thị.

# Chạy

Để chạy project, bạn cần:
- Java 21(+):
    - [Windows](https://adoptium.net/download?link=https%3A%2F%2Fgithub.com%2Fadoptium%2Ftemurin21-binaries%2Freleases%2Fdownload%2Fjdk-21.0.10%252B7%2FOpenJDK21U-jdk_x64_windows_hotspot_21.0.10_7.msi&vendor=Adoptium)
    - Linux: Sử dụng trình quản lý gói của distro của bạn
    - NixOS: Chạy `nix develop`
- Gradle (tùy chọn?)
    - [Windows](https://docs.gradle.org/current/userguide/installation.html#windows_installation) (Ai mà biết cái này hoạt động thế nào)
    - Linux: Sử dụng trình quản lý gói của distro của bạn
    - NixOS: Chạy `nix develop`

Sau đó import project như một project gradle vào IDE của bạn. Nếu bạn không sử
dụng IDE, bạn có thể bỏ qua bước này và chỉ cần di chuyển đến thư mục chứa
project.

Để chạy, bạn có thể dùng lệnh
```sh
gradle run
```

Hoặc nếu bạn không có gradle, thay thế lệnh trên bằng
```sh
./gradlew run
```

Trên Windows (lệnh dưới đây chưa được thử nghiệm)
```batch
.\gradlew.bat run
```

# Đóng góp

Để đóng góp cho dự án này, hãy tạo một branch mới và đặt tên theo quy tắc sau:
- feature/<Tên tính năng (chú ý ngắn gọn)> với những đóng góp tính năng
- fix/<Tên lỗi giải quyết (chú ý ngắn gọn)> với những đóng góp sửa lỗi

Và tạo một pull request với đầy đủ mô tả

*Chú ý:* Đừng bao giờ commit trực tiếp lên `main`, sở dĩ `main` luôn luôn phải
là một trạng thái hoạt động của project. Việc này đảm bảo nếu việc thêm tính
năng/sửa lỗi có xảy ra sai sót và bạn muốn bắt đầu lại từ đầu, nhánh `main` sẽ
luôn là một điểm an toàn để quay về. Chi tiết: [Trunk based development](https://trunkbaseddevelopment.com/)
