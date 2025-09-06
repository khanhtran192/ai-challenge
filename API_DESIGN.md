# 📋 Thiết kế Chi tiết API cho Hệ thống Quản lý Văn bản

## Tổng quan

Tài liệu này mô tả chi tiết các API endpoints cho hệ thống quản lý văn bản với khả năng phát hiện dữ liệu nhạy cảm. Hệ thống hỗ trợ 6 use case chính từ đăng nhập đến quản lý phân quyền.

## 🔐 Use Case 1: Đăng nhập và Xác thực Người dùng

### 1.1 API Đăng nhập

**Endpoint:** `POST /api/auth/login`

**Request:**

```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response thành công (200):**

```json
{
  "success": true,
  "data": {
    "user": {
      "id": 1,
      "fullName": "Nguyễn Văn A",
      "email": "user@example.com",
      "role": "user",
      "createdAt": "2024-01-01T10:00:00Z"
    },
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "refresh_token_here",
    "expiresIn": 3600
  },
  "message": "Đăng nhập thành công"
}
```

**Response lỗi (401):**

```json
{
  "success": false,
  "error": {
    "code": "INVALID_CREDENTIALS",
    "message": "Email hoặc mật khẩu không chính xác"
  }
}
```

**Luồng logic:**

1. Validate định dạng email và độ dài password
2. Tìm user theo email trong database
3. So sánh password hash với BCrypt
4. Nếu hợp lệ: tạo JWT token và refresh token
5. Ghi audit log "USER_LOGIN"
6. Trả về thông tin user và token

### 1.2 API Đăng xuất

**Endpoint:** `POST /api/auth/logout`

**Headers:** `Authorization: Bearer <token>`

**Response (200):**

```json
{
  "success": true,
  "message": "Đăng xuất thành công"
}
```

**Luồng logic:**

1. Validate JWT token
2. Thêm token vào blacklist
3. Ghi audit log "USER_LOGOUT"

### 1.3 API Refresh Token

**Endpoint:** `POST /api/auth/refresh`

**Request:**

```json
{
  "refreshToken": "refresh_token_here"
}
```

**Response (200):**

```json
{
  "success": true,
  "data": {
    "token": "new_jwt_token",
    "expiresIn": 3600
  }
}
```

## 📤 Use Case 2: Upload Tài liệu

### 2.1 API Upload Tài liệu

**Endpoint:** `POST /api/documents/upload`

**Headers:**

- `Authorization: Bearer <token>`
- `Content-Type: multipart/form-data`

**Request (Form Data):**

```
file: [File] (PDF/DOCX, max 50MB)
```

**Response thành công (201):**

```json
{
  "success": true,
  "data": {
    "document": {
      "id": 123,
      "fileName": "contract.pdf",
      "fileType": "PDF",
      "fileSize": 2048576,
      "status": "uploaded",
      "uploadedAt": "2024-01-01T10:30:00Z",
      "owner": {
        "id": 1,
        "fullName": "Nguyễn Văn A",
        "email": "user@example.com"
      }
    }
  },
  "message": "Upload tài liệu thành công. Đang bắt đầu quá trình rà quét."
}
```

**Response lỗi - File không hợp lệ (400):**

```json
{
  "success": false,
  "error": {
    "code": "INVALID_FILE_TYPE",
    "message": "Chỉ chấp nhận file PDF hoặc DOCX"
  }
}
```

**Response lỗi - File quá lớn (413):**

```json
{
  "success": false,
  "error": {
    "code": "FILE_TOO_LARGE",
    "message": "Kích thước file không được vượt quá 50MB"
  }
}
```

**Luồng logic:**

1. Validate JWT token và quyền user
2. Kiểm tra file type (PDF/DOCX) và size (<= 50MB)
3. Tạo unique filename và lưu file vào storage
4. Tạo Document record với status="uploaded"
5. Đưa vào queue để rà quét (async)
6. Ghi audit log "DOCUMENT_UPLOADED"
7. Trả về thông tin document

### 2.2 API Lấy danh sách tài liệu của user

**Endpoint:** `GET /api/documents/my-documents`

**Headers:** `Authorization: Bearer <token>`

**Query Parameters:**

```
page: 0 (default)
size: 20 (default)
status: uploaded|scanning|ready|sensitive|error (optional)
sortBy: uploadedAt (default)
sortDir: desc (default)
```

**Response (200):**

```json
{
  "success": true,
  "data": {
    "documents": [
      {
        "id": 123,
        "fileName": "contract.pdf",
        "fileType": "PDF",
        "fileSize": 2048576,
        "status": "ready",
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

**Luồng logic:**

1. Validate JWT token
2. Query documents thuộc về user hiện tại
3. Apply filters và pagination
4. Trả về danh sách với thông tin cơ bản

## 🔍 Use Case 3: Rà quét tài liệu có dữ liệu nhạy cảm

### 3.1 API Kiểm tra trạng thái rà quét

**Endpoint:** `GET /api/documents/{documentId}/scan-status`

**Headers:** `Authorization: Bearer <token>`

**Response - Đang quét (200):**

```json
{
  "success": true,
  "data": {
    "documentId": 123,
    "status": "scanning",
    "progress": 45,
    "message": "Đang rà quét tài liệu...",
    "startedAt": "2024-01-01T10:31:00Z"
  }
}
```

**Response - Hoàn thành (200):**

```json
{
  "success": true,
  "data": {
    "documentId": 123,
    "status": "ready",
    "progress": 100,
    "message": "Rà quét hoàn tất. Không phát hiện dữ liệu nhạy cảm.",
    "startedAt": "2024-01-01T10:31:00Z",
    "completedAt": "2024-01-01T10:33:00Z",
    "scanResult": {
      "hasSensitiveData": false,
      "sensitiveCount": 0,
      "types": []
    }
  }
}
```

**Response - Phát hiện dữ liệu nhạy cảm (200):**

```json
{
  "success": true,
  "data": {
    "documentId": 123,
    "status": "sensitive",
    "progress": 100,
    "message": "Rà quét hoàn tất. Phát hiện dữ liệu nhạy cảm.",
    "completedAt": "2024-01-01T10:33:00Z",
    "scanResult": {
      "hasSensitiveData": true,
      "sensitiveCount": 3,
      "types": ["CMND", "SĐT", "EMAIL"],
      "details": [
        {
          "id": 1,
          "infoType": "CMND",
          "content": "123456789",
          "pageNumber": 1,
          "position": "x:100,y:200,w:150,h:20"
        }
      ]
    }
  }
}
```

### 3.2 API Webhook cho hệ thống AI (Internal)

**Endpoint:** `POST /api/internal/documents/{documentId}/scan-result`

**Headers:** `X-Internal-Key: <internal_secret>`

**Request:**

```json
{
  "documentId": 123,
  "status": "completed",
  "hasSensitiveData": true,
  "sensitiveData": [
    {
      "infoType": "CMND",
      "content": "123456789",
      "pageNumber": 1,
      "position": "x:100,y:200,w:150,h:20",
      "confidence": 0.95
    },
    {
      "infoType": "SĐT",
      "content": "0901234567",
      "pageNumber": 1,
      "position": "x:200,y:300,w:120,h:20",
      "confidence": 0.89
    }
  ]
}
```

**Response (200):**

```json
{
  "success": true,
  "message": "Cập nhật kết quả rà quét thành công"
}
```

**Luồng logic:**

1. Validate internal secret key
2. Cập nhật Document status
3. Lưu SensitiveInfo records nếu có
4. Ghi audit log "DOCUMENT_SCANNED"
5. Gửi notification cho owner (nếu có)

### 3.3 API Retry rà quét (khi lỗi)

**Endpoint:** `POST /api/documents/{documentId}/retry-scan`

**Headers:** `Authorization: Bearer <token>`

**Response (200):**

```json
{
  "success": true,
  "message": "Đã đưa tài liệu vào hàng đợi rà quét lại"
}
```

**Luồng logic:**

1. Validate user có quyền (owner hoặc admin)
2. Kiểm tra document status = "error"
3. Reset status = "uploaded"
4. Đưa lại vào queue
5. Ghi audit log "DOCUMENT_RETRY_SCAN"

## 👁️ Use Case 4: Xem Tài liệu

### 4.1 API Lấy thông tin chi tiết tài liệu

**Endpoint:** `GET /api/documents/{documentId}`

**Headers:** `Authorization: Bearer <token>`

**Response - Tài liệu bình thường (200):**

```json
{
  "success": true,
  "data": {
    "document": {
      "id": 123,
      "fileName": "contract.pdf",
      "fileType": "PDF",
      "fileSize": 2048576,
      "status": "ready",
      "uploadedAt": "2024-01-01T10:30:00Z",
      "owner": {
        "id": 1,
        "fullName": "Nguyễn Văn A",
        "email": "user@example.com"
      },
      "hasSensitiveData": false,
      "sensitiveCount": 0,
      "canView": true,
      "canShare": true
    }
  }
}
```

**Response - Tài liệu có dữ liệu nhạy cảm (200):**

```json
{
  "success": true,
  "data": {
    "document": {
      "id": 124,
      "fileName": "personal_info.pdf",
      "fileType": "PDF",
      "fileSize": 1536000,
      "status": "sensitive",
      "uploadedAt": "2024-01-01T11:00:00Z",
      "owner": {
        "id": 1,
        "fullName": "Nguyễn Văn A"
      },
      "hasSensitiveData": true,
      "sensitiveCount": 5,
      "sensitiveTypes": ["CMND", "SĐT", "EMAIL"],
      "canView": true,
      "canShare": true
    },
    "warning": "Tài liệu này chứa thông tin nhạy cảm. Vui lòng xử lý cẩn thận."
  }
}
```

**Response - Không có quyền (403):**

```json
{
  "success": false,
  "error": {
    "code": "ACCESS_DENIED",
    "message": "Bạn không có quyền truy cập tài liệu này"
  }
}
```

**Response - Tài liệu chưa quét xong (423):**

```json
{
  "success": false,
  "error": {
    "code": "DOCUMENT_PROCESSING",
    "message": "Tài liệu đang được xử lý. Vui lòng thử lại sau.",
    "status": "scanning",
    "progress": 45
  }
}
```

### 4.2 API Xem nội dung tài liệu

**Endpoint:** `GET /api/documents/{documentId}/content`

**Headers:** `Authorization: Bearer <token>`

**Query Parameters:**

```
page: 1 (optional, cho PDF nhiều trang)
highlight: true (default, highlight sensitive data)
```

**Response (200):**

```json
{
  "success": true,
  "data": {
    "documentId": 123,
    "content": {
      "type": "pdf",
      "totalPages": 5,
      "currentPage": 1,
      "contentUrl": "/api/documents/123/download?token=temp_token_here"
    },
    "sensitiveData": [
      {
        "id": 1,
        "infoType": "CMND",
        "pageNumber": 1,
        "position": "x:100,y:200,w:150,h:20",
        "masked": true
      }
    ]
  },
  "warning": "Tài liệu chứa 3 thông tin nhạy cảm đã được đánh dấu"
}
```

### 4.3 API Download tài liệu

**Endpoint:** `GET /api/documents/{documentId}/download`

**Headers:** `Authorization: Bearer <token>`

**Query Parameters:**

```
temp_token: <temporary_download_token> (expires in 5 minutes)
```

**Response (200):**

- Content-Type: application/pdf hoặc application/vnd.openxmlformats-officedocument.wordprocessingml.document
- Content-Disposition: attachment; filename="contract.pdf"
- File binary data

**Luồng logic:**

1. Validate JWT token và quyền truy cập
2. Kiểm tra document status (phải là ready hoặc sensitive)
3. Tạo temporary download token (5 phút)
4. Ghi audit log "DOCUMENT_VIEWED"
5. Stream file content

### 4.4 API Lấy chi tiết thông tin nhạy cảm

**Endpoint:** `GET /api/documents/{documentId}/sensitive-info`

**Headers:** `Authorization: Bearer <token>`

**Response (200):**

```json
{
  "success": true,
  "data": {
    "documentId": 123,
    "sensitiveInfo": [
      {
        "id": 1,
        "infoType": "CMND",
        "content": "123***789",
        "pageNumber": 1,
        "position": "x:100,y:200,w:150,h:20",
        "detectedAt": "2024-01-01T10:33:00Z",
        "confidence": 0.95
      },
      {
        "id": 2,
        "infoType": "SĐT",
        "content": "090***4567",
        "pageNumber": 1,
        "position": "x:200,y:300,w:120,h:20",
        "detectedAt": "2024-01-01T10:33:00Z",
        "confidence": 0.89
      }
    ],
    "summary": {
      "totalCount": 2,
      "typesSummary": {
        "CMND": 1,
        "SĐT": 1
      }
    }
  }
}
```

**Luồng logic:**

1. Validate user có quyền xem document
2. Lấy danh sách SensitiveInfo
3. Mask nội dung nhạy cảm (hiển thị một phần)
4. Ghi audit log "SENSITIVE_INFO_VIEWED"

## 🤝 Use Case 5: Chia sẻ Tài liệu

### 5.1 API Chia sẻ tài liệu với người khác

**Endpoint:** `POST /api/documents/{documentId}/share`

**Headers:** `Authorization: Bearer <token>`

**Request:**

```json
{
  "userEmail": "colleague@example.com",
  "permission": "view",
  "note": "Tài liệu hợp đồng cần xem xét"
}
```

**Response thành công (201):**

```json
{
  "success": true,
  "data": {
    "share": {
      "id": 456,
      "document": {
        "id": 123,
        "fileName": "contract.pdf"
      },
      "user": {
        "id": 2,
        "fullName": "Trần Thị B",
        "email": "colleague@example.com"
      },
      "permission": "view",
      "grantedAt": "2024-01-01T12:00:00Z",
      "grantedBy": {
        "id": 1,
        "fullName": "Nguyễn Văn A"
      }
    }
  },
  "message": "Chia sẻ tài liệu thành công"
}
```

**Response lỗi - Không phải chủ sở hữu (403):**

```json
{
  "success": false,
  "error": {
    "code": "NOT_OWNER",
    "message": "Chỉ chủ sở hữu mới có thể chia sẻ tài liệu"
  }
}
```

**Response lỗi - User không tồn tại (404):**

```json
{
  "success": false,
  "error": {
    "code": "USER_NOT_FOUND",
    "message": "Không tìm thấy người dùng với email này"
  }
}
```

**Luồng logic:**

1. Validate JWT token và quyền owner
2. Tìm user theo email
3. Kiểm tra không tự chia sẻ với chính mình
4. Kiểm tra chưa được chia sẻ trước đó
5. Tạo DocumentShare record
6. Ghi audit log "DOCUMENT_SHARED"
7. Gửi email thông báo (optional)

### 5.2 API Lấy danh sách người được chia sẻ

**Endpoint:** `GET /api/documents/{documentId}/shares`

**Headers:** `Authorization: Bearer <token>`

**Response (200):**

```json
{
  "success": true,
  "data": {
    "documentId": 123,
    "shares": [
      {
        "id": 456,
        "user": {
          "id": 2,
          "fullName": "Trần Thị B",
          "email": "colleague@example.com"
        },
        "permission": "view",
        "grantedAt": "2024-01-01T12:00:00Z"
      }
    ],
    "totalShares": 1
  }
}
```

### 5.3 API Thu hồi quyền chia sẻ

**Endpoint:** `DELETE /api/documents/{documentId}/shares/{shareId}`

**Headers:** `Authorization: Bearer <token>`

**Response (200):**

```json
{
  "success": true,
  "message": "Thu hồi quyền truy cập thành công"
}
```

**Luồng logic:**

1. Validate user là owner của document
2. Xóa DocumentShare record
3. Ghi audit log "DOCUMENT_SHARE_REVOKED"

### 5.4 API Lấy danh sách tài liệu được chia sẻ cho mình

**Endpoint:** `GET /api/documents/shared-with-me`

**Headers:** `Authorization: Bearer <token>`

**Query Parameters:**

```
page: 0 (default)
size: 20 (default)
```

**Response (200):**

```json
{
  "success": true,
  "data": {
    "documents": [
      {
        "id": 123,
        "fileName": "contract.pdf",
        "fileType": "PDF",
        "fileSize": 2048576,
        "status": "ready",
        "owner": {
          "id": 1,
          "fullName": "Nguyễn Văn A",
          "email": "user@example.com"
        },
        "permission": "view",
        "sharedAt": "2024-01-01T12:00:00Z",
        "hasSensitiveData": false
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

**Luồng logic:**

1. Query DocumentShare records where user = current user
2. Join với Document và Owner info
3. Apply pagination
4. Trả về danh sách

## 👨‍💼 Use Case 6: Quản lý phân quyền tài liệu (Admin)

### 6.1 API Lấy danh sách tất cả tài liệu (Admin)

**Endpoint:** `GET /api/admin/documents`

**Headers:** `Authorization: Bearer <token>` (Role: admin)

**Query Parameters:**

```
page: 0 (default)
size: 20 (default)
status: uploaded|scanning|ready|sensitive|error (optional)
ownerId: 123 (optional, filter by owner)
hasSensitiveData: true|false (optional)
sortBy: uploadedAt (default)
sortDir: desc (default)
```

**Response (200):**

```json
{
  "success": true,
  "data": {
    "documents": [
      {
        "id": 123,
        "fileName": "contract.pdf",
        "fileType": "PDF",
        "fileSize": 2048576,
        "status": "ready",
        "uploadedAt": "2024-01-01T10:30:00Z",
        "owner": {
          "id": 1,
          "fullName": "Nguyễn Văn A",
          "email": "user@example.com"
        },
        "hasSensitiveData": false,
        "sensitiveCount": 0,
        "shareCount": 2
      }
    ],
    "pagination": {
      "page": 0,
      "size": 20,
      "totalElements": 50,
      "totalPages": 3
    },
    "statistics": {
      "totalDocuments": 50,
      "documentsWithSensitiveData": 12,
      "statusSummary": {
        "ready": 35,
        "sensitive": 12,
        "scanning": 2,
        "error": 1
      }
    }
  }
}
```

### 6.2 API Xem chi tiết quyền truy cập của một tài liệu

**Endpoint:** `GET /api/admin/documents/{documentId}/permissions`

**Headers:** `Authorization: Bearer <token>` (Role: admin)

**Response (200):**

```json
{
  "success": true,
  "data": {
    "document": {
      "id": 123,
      "fileName": "contract.pdf",
      "owner": {
        "id": 1,
        "fullName": "Nguyễn Văn A",
        "email": "user@example.com"
      }
    },
    "permissions": [
      {
        "id": 456,
        "user": {
          "id": 2,
          "fullName": "Trần Thị B",
          "email": "colleague@example.com"
        },
        "permission": "view",
        "grantedAt": "2024-01-01T12:00:00Z",
        "grantedBy": "owner"
      }
    ],
    "totalShares": 1
  }
}
```

### 6.3 API Cập nhật quyền truy cập (Admin)

**Endpoint:** `PUT /api/admin/documents/{documentId}/permissions/{shareId}`

**Headers:** `Authorization: Bearer <token>` (Role: admin)

**Request:**

```json
{
  "permission": "view",
  "action": "update"
}
```

**Response (200):**

```json
{
  "success": true,
  "data": {
    "share": {
      "id": 456,
      "permission": "view",
      "updatedAt": "2024-01-01T14:00:00Z",
      "updatedBy": {
        "id": 999,
        "fullName": "Admin User"
      }
    }
  },
  "message": "Cập nhật quyền truy cập thành công"
}
```

### 6.4 API Thu hồi quyền truy cập (Admin)

**Endpoint:** `DELETE /api/admin/documents/{documentId}/permissions/{shareId}`

**Headers:** `Authorization: Bearer <token>` (Role: admin)

**Response (200):**

```json
{
  "success": true,
  "message": "Thu hồi quyền truy cập thành công"
}
```

**Luồng logic:**

1. Validate admin role
2. Kiểm tra không thể xóa quyền của owner
3. Xóa DocumentShare record
4. Ghi audit log "ADMIN_REVOKED_ACCESS"

### 6.5 API Thống kê hệ thống (Admin)

**Endpoint:** `GET /api/admin/statistics`

**Headers:** `Authorization: Bearer <token>` (Role: admin)

**Query Parameters:**

```
period: day|week|month|year (default: month)
startDate: 2024-01-01 (optional)
endDate: 2024-01-31 (optional)
```

**Response (200):**

```json
{
  "success": true,
  "data": {
    "overview": {
      "totalUsers": 150,
      "totalDocuments": 1250,
      "documentsWithSensitiveData": 245,
      "totalShares": 450,
      "storageUsed": "15.2GB"
    },
    "trends": {
      "uploadsPerDay": [
        { "date": "2024-01-01", "count": 12 },
        { "date": "2024-01-02", "count": 8 }
      ],
      "sensitiveDataDetections": [
        { "date": "2024-01-01", "count": 3 },
        { "date": "2024-01-02", "count": 1 }
      ]
    },
    "sensitiveDataTypes": {
      "CMND": 89,
      "SĐT": 156,
      "EMAIL": 78,
      "CCCD": 45
    },
    "topUsers": [
      {
        "userId": 1,
        "fullName": "Nguyễn Văn A",
        "documentCount": 45,
        "sensitiveDocumentCount": 8
      }
    ]
  }
}
```

### 6.6 API Audit Logs (Admin)

**Endpoint:** `GET /api/admin/audit-logs`

**Headers:** `Authorization: Bearer <token>` (Role: admin)

**Query Parameters:**

```
page: 0 (default)
size: 50 (default)
userId: 123 (optional)
documentId: 456 (optional)
action: LOGIN|UPLOAD|VIEW|SHARE (optional)
startDate: 2024-01-01T00:00:00Z (optional)
endDate: 2024-01-31T23:59:59Z (optional)
```

**Response (200):**

```json
{
  "success": true,
  "data": {
    "auditLogs": [
      {
        "id": 1001,
        "action": "DOCUMENT_VIEWED",
        "user": {
          "id": 1,
          "fullName": "Nguyễn Văn A",
          "email": "user@example.com"
        },
        "document": {
          "id": 123,
          "fileName": "contract.pdf"
        },
        "detail": "Viewed document with sensitive data",
        "createdAt": "2024-01-01T15:30:00Z",
        "ipAddress": "192.168.1.100",
        "userAgent": "Mozilla/5.0..."
      }
    ],
    "pagination": {
      "page": 0,
      "size": 50,
      "totalElements": 2500,
      "totalPages": 50
    }
  }
}
```

**Luồng logic:**

1. Validate admin role
2. Apply filters và pagination
3. Join với User và Document info
4. Trả về audit trail

## 📊 Tổng quan Kiến trúc API

### 🔧 Middleware và Security

```
1. Authentication Middleware
   - Validate JWT token
   - Load user context
   - Handle token expiry

2. Authorization Middleware
   - Check user roles
   - Verify document ownership
   - Check sharing permissions

3. Rate Limiting
   - Upload: 10 files/hour per user
   - API calls: 1000 requests/hour per user

4. Audit Logging
   - Log all sensitive operations
   - Include user, action, timestamp, IP
```

### 🗂️ Error Handling Standards

```json
{
  "success": false,
  "error": {
    "code": "ERROR_CODE",
    "message": "Thông báo lỗi cho user",
    "details": "Chi tiết kỹ thuật (optional)",
    "timestamp": "2024-01-01T10:00:00Z",
    "traceId": "uuid-here"
  }
}
```

### 📈 Performance Considerations

```
1. File Upload
   - Chunked upload cho file lớn
   - Virus scanning trước khi lưu
   - CDN cho file download

2. Database
   - Index trên user_id, document_id, status
   - Pagination cho tất cả list APIs
   - Connection pooling

3. Caching
   - Redis cache cho session
   - Cache document metadata
   - Cache scan results

4. Background Jobs
   - Queue system cho AI scanning
   - Retry mechanism cho failed jobs
   - Progress tracking
```

### 🔒 Security Features

```
1. Input Validation
   - File type whitelist
   - File size limits
   - SQL injection protection

2. Data Protection
   - Encrypt sensitive data at rest
   - Mask sensitive content in responses
   - Secure file storage

3. Access Control
   - Role-based permissions
   - Document-level access control
   - Admin override capabilities

4. Monitoring
   - Failed login attempts
   - Suspicious access patterns
   - Data breach detection
```

### 🚀 API Versioning

```
Base URL: /api/v1/
Headers:
  - Accept: application/json
  - Content-Type: application/json
  - Authorization: Bearer <token>
  - X-API-Version: 1.0
```

## 📋 Tóm tắt Endpoints

### Authentication

- `POST /api/auth/login` - Đăng nhập
- `POST /api/auth/logout` - Đăng xuất
- `POST /api/auth/refresh` - Refresh token

### Documents

- `POST /api/documents/upload` - Upload tài liệu
- `GET /api/documents/my-documents` - Danh sách tài liệu của user
- `GET /api/documents/{id}` - Chi tiết tài liệu
- `GET /api/documents/{id}/content` - Xem nội dung
- `GET /api/documents/{id}/download` - Download tài liệu
- `GET /api/documents/{id}/sensitive-info` - Chi tiết thông tin nhạy cảm
- `GET /api/documents/{id}/scan-status` - Trạng thái rà quét
- `POST /api/documents/{id}/retry-scan` - Retry rà quét

### Sharing

- `POST /api/documents/{id}/share` - Chia sẻ tài liệu
- `GET /api/documents/{id}/shares` - Danh sách người được chia sẻ
- `DELETE /api/documents/{id}/shares/{shareId}` - Thu hồi chia sẻ
- `GET /api/documents/shared-with-me` - Tài liệu được chia sẻ cho mình

### Admin

- `GET /api/admin/documents` - Tất cả tài liệu (admin)
- `GET /api/admin/documents/{id}/permissions` - Quyền truy cập tài liệu
- `PUT /api/admin/documents/{id}/permissions/{shareId}` - Cập nhật quyền
- `DELETE /api/admin/documents/{id}/permissions/{shareId}` - Thu hồi quyền
- `GET /api/admin/statistics` - Thống kê hệ thống
- `GET /api/admin/audit-logs` - Audit logs

### Internal

- `POST /api/internal/documents/{id}/scan-result` - Webhook AI scanning

## 🎯 Kết luận

Thiết kế API này đã bao phủ đầy đủ tất cả 6 use case với các tính năng:

- ✅ Authentication & Authorization hoàn chỉnh
- ✅ File upload với validation
- ✅ AI scanning integration
- ✅ Document viewing với sensitive data handling
- ✅ Sharing mechanism
- ✅ Admin management capabilities
- ✅ Comprehensive audit logging
- ✅ Error handling và security best practices

Tất cả API đều tuân theo RESTful principles và có response format nhất quán, dễ dàng integrate với frontend và external systems.
