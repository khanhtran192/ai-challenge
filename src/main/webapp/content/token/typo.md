# Typography Tokens - Design System V2 (Web)

Tài liệu này mô tả các token typography được sử dụng trong design system của dự án, được trích xuất từ Figma design system.

## Cấu trúc Typography Tokens

Design system sử dụng hệ thống text styles có cấu trúc rõ ràng với các categories khác nhau, mỗi category có các sizes và weights phù hợp.

### Naming Convention:

- **Format**: `{Category}/{Size}/{Weight}`
- **Categories**: Display, Heading, Body, Caption, Overline
- **Sizes**: XL, L, M, S, XS
- **Weights**: Regular, Medium, SemiBold, Bold

## 1. Display Typography

Được sử dụng cho các tiêu đề lớn nhất, hero sections và landing pages.

| Token               | Font Size | Line Height | Font Weight    | Letter Spacing | Usage                      |
| ------------------- | --------- | ----------- | -------------- | -------------- | -------------------------- |
| Display/XL/Bold     | 72px      | 88px        | Bold (700)     | -0.02em        | Hero titles, main headings |
| Display/XL/SemiBold | 72px      | 88px        | SemiBold (600) | -0.02em        | Large display text         |
| Display/XL/Medium   | 72px      | 88px        | Medium (500)   | -0.02em        | Prominent headings         |
| Display/XL/Regular  | 72px      | 88px        | Regular (400)  | -0.02em        | Display text               |
| Display/L/Bold      | 60px      | 72px        | Bold (700)     | -0.02em        | Section titles             |
| Display/L/SemiBold  | 60px      | 72px        | SemiBold (600) | -0.02em        | Large headings             |
| Display/L/Medium    | 60px      | 72px        | Medium (500)   | -0.02em        | Feature titles             |
| Display/L/Regular   | 60px      | 72px        | Regular (400)  | -0.02em        | Display content            |
| Display/M/Bold      | 48px      | 56px        | Bold (700)     | -0.01em        | Page headings              |
| Display/M/SemiBold  | 48px      | 56px        | SemiBold (600) | -0.01em        | Section headings           |
| Display/M/Medium    | 48px      | 56px        | Medium (500)   | -0.01em        | Card titles                |
| Display/M/Regular   | 48px      | 56px        | Regular (400)  | -0.01em        | Display text               |
| Display/S/Bold      | 36px      | 44px        | Bold (700)     | -0.01em        | Component titles           |
| Display/S/SemiBold  | 36px      | 44px        | SemiBold (600) | -0.01em        | Modal headings             |
| Display/S/Medium    | 36px      | 44px        | Medium (500)   | -0.01em        | Card headings              |
| Display/S/Regular   | 36px      | 44px        | Regular (400)  | -0.01em        | Display content            |

## 2. Heading Typography

Được sử dụng cho các tiêu đề của sections, articles và components.

| Token               | Font Size | Line Height | Font Weight    | Letter Spacing | Usage                  |
| ------------------- | --------- | ----------- | -------------- | -------------- | ---------------------- |
| Heading/XL/Bold     | 30px      | 38px        | Bold (700)     | -0.01em        | Main article titles    |
| Heading/XL/SemiBold | 30px      | 38px        | SemiBold (600) | -0.01em        | Section headings       |
| Heading/XL/Medium   | 30px      | 38px        | Medium (500)   | -0.01em        | Component headings     |
| Heading/XL/Regular  | 30px      | 38px        | Regular (400)  | -0.01em        | Content headings       |
| Heading/L/Bold      | 24px      | 32px        | Bold (700)     | -0.01em        | Subsection titles      |
| Heading/L/SemiBold  | 24px      | 32px        | SemiBold (600) | -0.01em        | Card titles            |
| Heading/L/Medium    | 24px      | 32px        | Medium (500)   | -0.01em        | Form section headings  |
| Heading/L/Regular   | 24px      | 32px        | Regular (400)  | -0.01em        | Content titles         |
| Heading/M/Bold      | 20px      | 28px        | Bold (700)     | 0em            | Component titles       |
| Heading/M/SemiBold  | 20px      | 28px        | SemiBold (600) | 0em            | Table headers          |
| Heading/M/Medium    | 20px      | 28px        | Medium (500)   | 0em            | List headings          |
| Heading/M/Regular   | 20px      | 28px        | Regular (400)  | 0em            | Content headings       |
| Heading/S/Bold      | 18px      | 24px        | Bold (700)     | 0em            | Small section titles   |
| Heading/S/SemiBold  | 18px      | 24px        | SemiBold (600) | 0em            | Form labels            |
| Heading/S/Medium    | 18px      | 24px        | Medium (500)   | 0em            | Card subtitles         |
| Heading/S/Regular   | 18px      | 24px        | Regular (400)  | 0em            | Content subtitles      |
| Heading/XS/Bold     | 16px      | 20px        | Bold (700)     | 0em            | Small headings         |
| Heading/XS/SemiBold | 16px      | 20px        | SemiBold (600) | 0em            | Component labels       |
| Heading/XS/Medium   | 16px      | 20px        | Medium (500)   | 0em            | List item headings     |
| Heading/XS/Regular  | 16px      | 20px        | Regular (400)  | 0em            | Small content headings |

## 3. Body Typography

Được sử dụng cho nội dung chính, paragraphs và text blocks.

| Token            | Font Size | Line Height | Font Weight    | Letter Spacing | Usage                |
| ---------------- | --------- | ----------- | -------------- | -------------- | -------------------- |
| Body/XL/Bold     | 20px      | 30px        | Bold (700)     | 0em            | Important paragraphs |
| Body/XL/SemiBold | 20px      | 30px        | SemiBold (600) | 0em            | Emphasized content   |
| Body/XL/Medium   | 20px      | 30px        | Medium (500)   | 0em            | Highlighted text     |
| Body/XL/Regular  | 20px      | 30px        | Regular (400)  | 0em            | Large body text      |
| Body/L/Bold      | 18px      | 28px        | Bold (700)     | 0em            | Key information      |
| Body/L/SemiBold  | 18px      | 28px        | SemiBold (600) | 0em            | Important text       |
| Body/L/Medium    | 18px      | 28px        | Medium (500)   | 0em            | Emphasized content   |
| Body/L/Regular   | 18px      | 28px        | Regular (400)  | 0em            | Standard body text   |
| Body/M/Bold      | 16px      | 24px        | Bold (700)     | 0em            | Button text, labels  |
| Body/M/SemiBold  | 16px      | 24px        | SemiBold (600) | 0em            | Form inputs          |
| Body/M/Medium    | 16px      | 24px        | Medium (500)   | 0em            | Navigation items     |
| Body/M/Regular   | 16px      | 24px        | Regular (400)  | 0em            | Default body text    |
| Body/S/Bold      | 14px      | 20px        | Bold (700)     | 0em            | Small important text |
| Body/S/SemiBold  | 14px      | 20px        | SemiBold (600) | 0em            | Table content        |
| Body/S/Medium    | 14px      | 20px        | Medium (500)   | 0em            | Secondary text       |
| Body/S/Regular   | 14px      | 20px        | Regular (400)  | 0em            | Small body text      |
| Body/XS/Bold     | 12px      | 16px        | Bold (700)     | 0em            | Tiny emphasized text |
| Body/XS/SemiBold | 12px      | 16px        | SemiBold (600) | 0em            | Small labels         |
| Body/XS/Medium   | 12px      | 16px        | Medium (500)   | 0em            | Helper text          |
| Body/XS/Regular  | 12px      | 16px        | Regular (400)  | 0em            | Small content        |

## 4. Caption Typography

Được sử dụng cho captions, metadata, timestamps và secondary information.

| Token              | Font Size | Line Height | Font Weight    | Letter Spacing | Usage                |
| ------------------ | --------- | ----------- | -------------- | -------------- | -------------------- |
| Caption/L/Bold     | 14px      | 20px        | Bold (700)     | 0em            | Important captions   |
| Caption/L/SemiBold | 14px      | 20px        | SemiBold (600) | 0em            | Photo captions       |
| Caption/L/Medium   | 14px      | 20px        | Medium (500)   | 0em            | Image descriptions   |
| Caption/L/Regular  | 14px      | 20px        | Regular (400)  | 0em            | Standard captions    |
| Caption/M/Bold     | 12px      | 16px        | Bold (700)     | 0em            | Small important info |
| Caption/M/SemiBold | 12px      | 16px        | SemiBold (600) | 0em            | Timestamps           |
| Caption/M/Medium   | 12px      | 16px        | Medium (500)   | 0em            | Metadata             |
| Caption/M/Regular  | 12px      | 16px        | Regular (400)  | 0em            | Small captions       |
| Caption/S/Bold     | 11px      | 16px        | Bold (700)     | 0.01em         | Tiny labels          |
| Caption/S/SemiBold | 11px      | 16px        | SemiBold (600) | 0.01em         | Status indicators    |
| Caption/S/Medium   | 11px      | 16px        | Medium (500)   | 0.01em         | Fine print           |
| Caption/S/Regular  | 11px      | 16px        | Regular (400)  | 0.01em         | Small metadata       |

## 5. Overline Typography

Được sử dụng cho labels, categories, tags và navigational elements.

| Token               | Font Size | Line Height | Font Weight    | Letter Spacing | Usage                 |
| ------------------- | --------- | ----------- | -------------- | -------------- | --------------------- |
| Overline/L/Bold     | 14px      | 20px        | Bold (700)     | 0.08em         | Important categories  |
| Overline/L/SemiBold | 14px      | 20px        | SemiBold (600) | 0.08em         | Section labels        |
| Overline/L/Medium   | 14px      | 20px        | Medium (500)   | 0.08em         | Category tags         |
| Overline/L/Regular  | 14px      | 20px        | Regular (400)  | 0.08em         | Standard overlines    |
| Overline/M/Bold     | 12px      | 16px        | Bold (700)     | 0.08em         | Small category labels |
| Overline/M/SemiBold | 12px      | 16px        | SemiBold (600) | 0.08em         | Navigation labels     |
| Overline/M/Medium   | 12px      | 16px        | Medium (500)   | 0.08em         | Filter tags           |
| Overline/M/Regular  | 12px      | 16px        | Regular (400)  | 0.08em         | Small overlines       |
| Overline/S/Bold     | 11px      | 16px        | Bold (700)     | 0.08em         | Tiny category labels  |
| Overline/S/SemiBold | 11px      | 16px        | SemiBold (600) | 0.08em         | Small tags            |
| Overline/S/Medium   | 11px      | 16px        | Medium (500)   | 0.08em         | Badge text            |
| Overline/S/Regular  | 11px      | 16px        | Regular (400)  | 0.08em         | Small overlines       |

## Font Information

### Primary Font Family

- **Font**: Được sử dụng font system default hoặc font được định nghĩa trong design system
- **Fallback**: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif

### Font Weights

- **Regular (400)**: Cho nội dung thông thường
- **Medium (500)**: Cho text cần nhấn mạnh nhẹ
- **SemiBold (600)**: Cho headings và labels quan trọng
- **Bold (700)**: Cho titles và text cần nhấn mạnh mạnh

## Hướng dẫn sử dụng

### 1. Hierarchy & Semantic Usage

**Display**: Dành cho hero sections, landing pages

```css
.hero-title {
  font-size: 72px;
  line-height: 88px;
  font-weight: 700;
  letter-spacing: -0.02em;
}
```

**Heading**: Dành cho titles của sections và components

```css
.section-title {
  font-size: 30px;
  line-height: 38px;
  font-weight: 600;
  letter-spacing: -0.01em;
}
```

**Body**: Dành cho nội dung chính

```css
.body-text {
  font-size: 16px;
  line-height: 24px;
  font-weight: 400;
  letter-spacing: 0em;
}
```

### 2. Responsive Typography

**Desktop**: Sử dụng sizes đầy đủ
**Tablet**: Giảm 1 level (XL → L, L → M, etc.)
**Mobile**: Giảm 2 levels hoặc sử dụng các sizes S, XS

### 3. Accessibility Guidelines

- **Contrast**: Đảm bảo contrast ratio tối thiểu 4.5:1 cho body text
- **Line Height**: Tối thiểu 1.5x font size cho body text
- **Font Size**: Tối thiểu 16px cho body text trên mobile
- **Letter Spacing**: Sử dụng negative letter spacing cho large headings

### 4. CSS Custom Properties Implementation

```css
:root {
  /* Display */
  --typography-display-xl-bold: 700 72px/88px var(--font-family);
  --typography-display-l-semibold: 600 60px/72px var(--font-family);

  /* Heading */
  --typography-heading-xl-bold: 700 30px/38px var(--font-family);
  --typography-heading-l-medium: 500 24px/32px var(--font-family);

  /* Body */
  --typography-body-m-regular: 400 16px/24px var(--font-family);
  --typography-body-s-medium: 500 14px/20px var(--font-family);

  /* Caption */
  --typography-caption-m-regular: 400 12px/16px var(--font-family);

  /* Overline */
  --typography-overline-m-semibold: 600 12px/16px var(--font-family);
}
```

### 5. Usage Best Practices

- **Consistency**: Luôn sử dụng predefined typography tokens
- **Hierarchy**: Tạo visual hierarchy rõ ràng bằng cách sử dụng sizes và weights phù hợp
- **Readability**: Đảm bảo line height và letter spacing phù hợp cho từng use case
- **Performance**: Sử dụng font loading strategies để tối ưu performance

### 6. Common Patterns

**Card Component**:

- Title: Heading/L/SemiBold
- Subtitle: Body/S/Medium
- Content: Body/M/Regular
- Metadata: Caption/M/Regular

**Form Component**:

- Label: Heading/S/SemiBold
- Input: Body/M/Regular
- Helper text: Caption/M/Regular
- Error text: Caption/M/Bold (with error color)

**Navigation**:

- Main nav: Body/M/Medium
- Breadcrumb: Caption/L/Regular
- Tab: Body/S/SemiBold

---

_Tài liệu này được tạo từ Design System V2 (Web) trên Figma và sẽ được cập nhật khi có thay đổi trong design system._
