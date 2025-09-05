# 🚀 AI Challenge - Team 5 CHANG LINH NGU LAM

```
    ______        __                         ___       __                            __
   / ____/  _____/ /_  ____ _____  ____ _   / (_)___  / /_     ____  ____ ___  __   / /___ _____ ___
  /___ \   / ___/ __ \/ __ `/ __ \/ __ `/  / / / __ \/ __ \   / __ \/ __ `/ / / /  / / __ `/ __ `__ \
 ____/ /  / /__/ / / / /_/ / / / / /_/ /  / / / / / / / / /  / / / / /_/ / /_/ /  / / /_/ / / / / / /
/_____/   \___/_/ /_/\__,_/_/ /_/\__, /  /_/_/_/ /_/_/ /_/  /_/ /_/\__, /\__,_/  /_/\__,_/_/ /_/ /_/
                                /____/                            /____/

```

## 👥 Giới thiệu nhóm

### 🌟 Team: **5 CHANG LINH NGU LAM**

### 🏢 Địa chỉ: **BIZ**

```
██████╗ ██╗███████╗
██╔══██╗██║╚══███╔╝
██████╔╝██║  ███╔╝
██╔══██╗██║ ███╔╝
██████╔╝██║███████╗
╚═════╝ ╚═╝╚══════╝
```

### 👑 **Leader:** Nguyen Minh Tien

### 🎯 **Members:**

- 🔥 Phan Nhat Minh
- 💪 Le Duc Long
- ⚡ Nguyen Thanh Tung
- 🚀 Tran Duy Khanh

---

## 🎮 Về dự án

Đây là ứng dụng AI Challenge được phát triển bởi nhóm **5 CHANG LINH NGU LAM** từ tổ chức **BIZ**.
Dự án được xây dựng trên nền tảng JHipster 8.11.0.

📚 **Tài liệu chi tiết cho developers:** [README-DEV.md](README-DEV.md)

## 🚀 Cách chạy dự án

### 📋 Yêu cầu hệ thống

- Java 17+
- Node.js & npm
- Docker (tùy chọn)

### ⚡ Khởi động nhanh

1. **Cài đặt dependencies:**

```bash
./npmw install
```

2. **Khởi động Redis (nếu cần):**

```bash
docker compose -f src/main/docker/redis.yml up -d
```

3. **Chạy ứng dụng (2 terminal riêng biệt):**

```bash
# Terminal 1: Backend
./mvnw

# Terminal 2: Frontend
./npmw start
```

4. **Truy cập ứng dụng:**
   - Frontend: http://localhost:4200
   - Backend: http://localhost:8080

### 🧪 Chạy test

```bash
# Backend tests
./mvnw verify

# Frontend tests
./npmw test
```

### 🏗️ Build Production

**Tạo file JAR:**

```bash
./mvnw -Pprod clean verify
java -jar target/*.jar
```

**Tạo file WAR:**

```bash
./mvnw -Pprod,war clean verify
```

### 🐳 Docker Support

**Khởi động services:**

```bash
docker compose -f src/main/docker/services.yml up -d
```

**Build Docker image:**

```bash
npm run java:docker
# Hoặc cho ARM64 (Mac M1/M2):
npm run java:docker:arm64
```

**Chạy toàn bộ app với Docker:**

```bash
docker compose -f src/main/docker/app.yml up -d
```

### 📊 Code Quality (Sonar)

```bash
docker compose -f src/main/docker/sonar.yml up -d
./mvnw -Pprod clean verify sonar:sonar -Dsonar.login=admin -Dsonar.password=admin
```

---

## 🎉 Cảm ơn

Được phát triển với ❤️ bởi team **5 CHANG LINH NGU LAM** từ **BIZ**!

```
🎯 "Code như một nghệ thuật, debug như một triết gia!" 🎯
```

---

_Để biết thêm chi tiết về development, vui lòng xem [README-DEV.md](README-DEV.md)_
