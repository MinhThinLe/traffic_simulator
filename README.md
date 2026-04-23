# Project OOP: Mô phỏng giao thông

Đây là repo cho bài tập lớn của môn Lập trình hướng đối tượng về chủ đề mô
phỏng giao thông đô thị.

# Chạy

Để chạy project, bạn cần:
- Java 21(-24):
    - [Windows](https://adoptium.net/download?link=https%3A%2F%2Fgithub.com%2Fadoptium%2Ftemurin21-binaries%2Freleases%2Fdownload%2Fjdk-21.0.10%252B7%2FOpenJDK21U-jdk_x64_windows_hotspot_21.0.10_7.msi&vendor=Adoptium)
    - Linux: Sử dụng trình quản lý gói của distro của bạn
    - NixOS: Chạy `nix develop`
- Gradle (tùy chọn)
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

hoặc nếu bạn dùng Windows
```batch
.\gradlew.bat run
```

# Đóng góp

Để đóng góp cho dự án này, trước hết, hãy đọc file `tasks.md` và xem bạn có thể
đóng góp gì. Sau đó, hãy tạo một branch mới và đặt tên theo quy tắc sau:
- feature/<Tên tính năng (chú ý ngắn gọn)> với những đóng góp tính năng
- fix/<Tên lỗi giải quyết (chú ý ngắn gọn)> với những đóng góp sửa lỗi
- meta/<Tên cải thiện (chú ý ngắn gọn)> với những đóng góp mang tính cải thiện
  về cách vận hành dự án

Và tạo một pull request với đầy đủ mô tả

**Chú ý:** Đừng bao giờ commit trực tiếp lên `main`, sở dĩ `main` luôn luôn phải
là một trạng thái hoạt động của project. Việc này đảm bảo nếu việc thêm tính
năng/sửa lỗi có xảy ra sai sót và bạn muốn bắt đầu lại từ đầu, nhánh `main` sẽ
luôn là một điểm an toàn để quay về. Chi tiết: [Trunk based development](https://trunkbaseddevelopment.com/).
Tất nhiên là cũng có ngoại lệ, chẳng hạn: những thay đổi nhỏ/không liên quan
đến code (cập nhật README.md).

## Quy tắc đặt tên

- Với biến/hàm, dùng quy chuẩn [camelCase](https://en.wikipedia.org/wiki/Camel_case)
- Với tên đối tượng/interface, dùng quy chuẩn [PascalCase](https://pascal-case.com/)
- Với hằng số, dùng quy chuẩn [SCREAMING_SNAKE_CASE](https://en.wikipedia.org/wiki/Snake_case) 
- Với tên module, dùng quy chuẩn [flatcase](https://text-case.com/guides/flat-case-guide)

Về cơ bản, đặt tên theo quy chuẩn của [Java](https://en.wikipedia.org/wiki/Naming_convention_(programming)#Java) 

## Lưu ý khi đặt tên

- Tên nên ngắn gọn, súc tích
- Tên nên phản ánh đúng mục đích
- Nên tránh dùng các tên 1 ký tự (a, i, j), trừ khi nó là đối tượng tạm
  thời/không quan trọng
