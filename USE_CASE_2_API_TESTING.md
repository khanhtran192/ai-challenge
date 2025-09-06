# 📋 Hướng dẫn Test Use Case 2: Upload Tài liệu

## Tổng quan

Use Case 2 đã được implement hoàn chỉnh với 2 API endpoints chính:

1. `POST /api/documents/upload` - Upload tài liệu
2. `GET /api/documents/my-documents` - Lấy danh sách tài liệu của user

## 🚀 Khởi chạy ứng dụng

```bash
# Khởi chạy database (nếu sử dụng Docker)
cd src/main/docker
docker-compose -f services.yml up -d

# Khởi chạy ứng dụng
./mvnw spring-boot:run
```

Ứng dụng sẽ chạy tại: `http://localhost:8080`

## 🔐 Authentication

Trước khi test các API documents, bạn cần đăng nhập để lấy JWT token:

### 1. Đăng nhập

```bash
curl -X POST http://localhost:8080/api/authenticate \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin@localhost.com",
    "password": "admin",
    "rememberMe": false
  }'
```

Response sẽ chứa `id_token` mà bạn sẽ sử dụng cho các request tiếp theo.

## 📤 Test API Upload Tài liệu

### 1. Upload file PDF hợp lệ

```bash
curl -X POST http://localhost:8080/api/documents/upload \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -F "file=@/path/to/your/document.pdf"
```

**Expected Response (201 Created):**

```json
{
  "success": true,
  "data": {
    "id": 1,
    "fileName": "document.pdf",
    "fileType": "PDF",
    "fileSize": 2048576,
    "status": "uploaded",
    "uploadedAt": "2024-01-01T10:30:00Z",
    "owner": {
      "id": 1,
      "fullName": "Administrator",
      "email": "admin@localhost.com"
    }
  },
  "message": "Upload tài liệu thành công. Đang bắt đầu quá trình rà quét."
}
```

### 2. Upload file DOCX hợp lệ

```bash
curl -X POST http://localhost:8080/api/documents/upload \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -F "file=@/path/to/your/document.docx"
```

### 3. Test validation - File không hợp lệ

```bash
curl -X POST http://localhost:8080/api/documents/upload \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -F "file=@/path/to/invalid.txt"
```

**Expected Response (400 Bad Request):**

```json
{
  "success": false,
  "error": {
    "code": "INVALID_FILE_TYPE",
    "message": "Chỉ chấp nhận file PDF hoặc DOCX"
  }
}
```

### 4. Test validation - File quá lớn

```bash
# Upload file > 50MB
curl -X POST http://localhost:8080/api/documents/upload \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -F "file=@/path/to/large-file.pdf"
```

**Expected Response (413 Payload Too Large):**

```json
{
  "success": false,
  "error": {
    "code": "FILE_TOO_LARGE",
    "message": "Kích thước file không được vượt quá 50MB"
  }
}
```

## 📋 Test API Danh sách Tài liệu

### 1. Lấy danh sách tài liệu cơ bản

```bash
curl -X GET http://localhost:8080/api/documents/my-documents \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

**Expected Response (200 OK):**

```json
{
  "success": true,
  "data": {
    "documents": [
      {
        "id": 1,
        "fileName": "document.pdf",
        "fileType": "PDF",
        "fileSize": 2048576,
        "status": "uploaded",
        "uploadedAt": "2024-01-01T10:30:00Z",
        "hasSensitiveData": false,
        "sensitiveCount": 0
      }
    ],
    "pagination": {
      "page": 0,
      "size": 20,
      "totalElements": 1,
      "totalPages": 1
    }
  }
}
```

### 2. Lấy danh sách với pagination

```bash
curl -X GET "http://localhost:8080/api/documents/my-documents?page=0&size=10" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### 3. Lọc theo status

```bash
curl -X GET "http://localhost:8080/api/documents/my-documents?status=uploaded" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### 4. Sắp xếp

```bash
curl -X GET "http://localhost:8080/api/documents/my-documents?sortBy=fileName&sortDir=asc" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## 🧪 Chạy Integration Tests

```bash
# Chạy tất cả tests
./mvnw test

# Chạy chỉ DocumentController tests
./mvnw test -Dtest=DocumentControllerIT
```

## ⚠️ Các trường hợp lỗi thường gặp

### 1. Unauthorized (401)

```json
{
  "success": false,
  "error": {
    "code": "UNAUTHORIZED",
    "message": "Người dùng chưa đăng nhập"
  }
}
```

**Giải pháp:** Đảm bảo bạn đã include JWT token trong header Authorization.

### 2. User Not Found (401)

```json
{
  "success": false,
  "error": {
    "code": "USER_NOT_FOUND",
    "message": "Không tìm thấy thông tin người dùng"
  }
}
```

**Giải pháp:** Đảm bảo user tồn tại trong database và JWT token hợp lệ.

### 3. Storage Error (500)

```json
{
  "success": false,
  "error": {
    "code": "STORAGE_ERROR",
    "message": "Lỗi lưu trữ file. Vui lòng thử lại."
  }
}
```

**Giải pháp:** Kiểm tra quyền ghi trong thư mục upload và dung lượng disk.

## 📁 Cấu hình File Storage

File được lưu tại: `${java.io.tmpdir}/ai-challenge/uploads`

Trên hệ thống Unix/Linux: `/tmp/ai-challenge/uploads`
Trên Windows: `C:\Users\[username]\AppData\Local\Temp\ai-challenge\uploads`

## 🔧 Cấu hình tùy chỉnh

Trong `application.yml`:

```yaml
application:
  file-storage:
    upload-dir: /custom/upload/path

spring:
  servlet:
    multipart:
      max-file-size: 50MB
      max-request-size: 50MB
```

## 📊 Database Schema

Sau khi upload thành công, dữ liệu được lưu trong bảng `DOCUMENTS`:

```sql
SELECT * FROM DOCUMENTS WHERE owner_id = [user_id];
```

Audit log được ghi trong bảng `AUDIT_LOGS`:

```sql
SELECT * FROM AUDIT_LOGS WHERE action = 'DOCUMENT_UPLOADED';
```

## ✅ Checklist Test

- [ ] Upload PDF file thành công
- [ ] Upload DOCX file thành công
- [ ] Validation file type hoạt động
- [ ] Validation file size hoạt động
- [ ] Lấy danh sách documents thành công
- [ ] Pagination hoạt động
- [ ] Filtering theo status hoạt động
- [ ] Sorting hoạt động
- [ ] Authentication bắt buộc
- [ ] File được lưu trữ đúng vị trí
- [ ] Audit log được ghi
- [ ] Integration tests pass

## 🎯 Kết luận

Use Case 2: Upload Tài liệu đã được implement hoàn chỉnh với:

✅ **API Upload** - Hỗ trợ PDF/DOCX, validation đầy đủ
✅ **API Danh sách** - Pagination, filtering, sorting
✅ **File Storage** - Lưu trữ an toàn với tên file unique
✅ **Audit Logging** - Ghi log mọi hành động
✅ **Error Handling** - Xử lý lỗi thống nhất
✅ **Security** - Authentication & authorization
✅ **Tests** - Integration tests đầy đủ

Hệ thống đã sẵn sàng cho Use Case 3: Rà quét tài liệu có dữ liệu nhạy cảm.
