# Color Tokens - Design System V2 (Web)

Tài liệu này mô tả các token màu sắc được sử dụng trong design system của dự án, được trích xuất từ Figma design system.

## Cấu trúc Token Màu

Design system sử dụng cấu trúc alias để tạo ra các token màu có ý nghĩa semantic, giúp duy trì tính nhất quán và dễ dàng thay đổi theme.

### Cấu trúc Naming Convention:

- **Global Colors**: `Global.{ColorFamily}.{ColorFamily}-{Weight}`
- **Alias Colors**: `Alias.{SemanticName}-{Weight}`

## 1. Primary Colors (Semantic 1)

Màu chính được sử dụng cho các element quan trọng nhất trong interface.

### Dark Blue Palette

| Token                | Alias          | Global Reference              | Hex       | RGB                |
| -------------------- | -------------- | ----------------------------- | --------- | ------------------ |
| Alias.Semantic1-1000 | Dark Blue 1000 | Global.DarkBlue.DarkBlue-1000 | `#192D39` | rgb(25, 45, 57)    |
| Alias.Semantic1-900  | Dark Blue 900  | Global.DarkBlue.DarkBlue-900  | `#273B4B` | rgb(39, 59, 75)    |
| Alias.Semantic1-800  | Dark Blue 800  | Global.DarkBlue.DarkBlue-800  | `#354A5E` | rgb(53, 74, 94)    |
| Alias.Semantic1-700  | Dark Blue 700  | Global.DarkBlue.DarkBlue-700  | `#435870` | rgb(67, 88, 112)   |
| Alias.Semantic1-600  | Dark Blue 600  | Global.DarkBlue.DarkBlue-600  | `#516682` | rgb(81, 102, 130)  |
| Alias.Semantic1-500  | Dark Blue 500  | Global.DarkBlue.DarkBlue-500  | `#6D83A7` | rgb(109, 131, 167) |
| Alias.Semantic1-400  | Dark Blue 400  | Global.DarkBlue.DarkBlue-400  | `#9BAFC8` | rgb(155, 175, 200) |
| Alias.Semantic1-300  | Dark Blue 300  | Global.DarkBlue.DarkBlue-300  | `#CADBE8` | rgb(202, 219, 232) |
| Alias.Semantic1-200  | Dark Blue 200  | Global.DarkBlue.DarkBlue-200  | `#ECF5FA` | rgb(236, 245, 250) |
| Alias.Semantic1-100  | Dark Blue 100  | Global.DarkBlue.DarkBlue-100  | `#F9FDFF` | rgb(249, 253, 255) |

### Blue Palette (Primary Actions)

| Token              | Alias     | Global Reference      | Hex       | RGB                |
| ------------------ | --------- | --------------------- | --------- | ------------------ |
| Alias.Primary-1000 | Blue 1000 | Global.Blue.Blue-1000 | `#00087C` | rgb(0, 8, 124)     |
| Alias.Primary-900  | Blue 900  | Global.Blue.Blue-900  | `#050D8C` | rgb(5, 13, 140)    |
| Alias.Primary-800  | Blue 800  | Global.Blue.Blue-800  | `#09129B` | rgb(9, 18, 155)    |
| Alias.Primary-700  | Blue 700  | Global.Blue.Blue-700  | `#0E16AB` | rgb(14, 22, 171)   |
| Alias.Primary-600  | Blue 600  | Global.Blue.Blue-600  | `#121BBB` | rgb(18, 27, 187)   |
| Alias.Primary-500  | Blue 500  | Global.Blue.Blue-500  | `#141ED2` | rgb(20, 30, 210)   |
| Alias.Primary-400  | Blue 400  | Global.Blue.Blue-400  | `#5F7EEB` | rgb(95, 126, 235)  |
| Alias.Primary-300  | Blue 300  | Global.Blue.Blue-300  | `#A3B7FD` | rgb(163, 183, 254) |
| Alias.Primary-200  | Blue 200  | Global.Blue.Blue-200  | `#DAE4FF` | rgb(218, 228, 255) |
| Alias.Primary-100  | Blue 100  | Global.Blue.Blue-100  | `#E9EFFF` | rgb(233, 239, 255) |

## 2. Secondary Colors (Semantic 2)

Màu phụ được sử dụng cho các element hỗ trợ và thông tin bổ sung.

### Light Blue Palette

| Token                | Alias           | Global Reference                | Hex       | RGB                |
| -------------------- | --------------- | ------------------------------- | --------- | ------------------ |
| Alias.Semantic2-1000 | Light Blue 1000 | Global.LightBlue.LightBlue-1000 | `#003E7C` | rgb(0, 62, 124)    |
| Alias.Semantic2-900  | Light Blue 900  | Global.LightBlue.LightBlue-900  | `#004996` | rgb(0, 73, 150)    |
| Alias.Semantic2-800  | Light Blue 800  | Global.LightBlue.LightBlue-800  | `#0054B0` | rgb(0, 84, 176)    |
| Alias.Semantic2-700  | Light Blue 700  | Global.LightBlue.LightBlue-700  | `#005FCB` | rgb(0, 95, 203)    |
| Alias.Semantic2-600  | Light Blue 600  | Global.LightBlue.LightBlue-600  | `#006AE5` | rgb(0, 106, 229)   |
| Alias.Semantic2-500  | Light Blue 500  | Global.LightBlue.LightBlue-500  | `#0075FF` | rgb(0, 117, 255)   |
| Alias.Semantic2-400  | Light Blue 400  | Global.LightBlue.LightBlue-400  | `#00A5FF` | rgb(0, 165, 255)   |
| Alias.Semantic2-300  | Light Blue 300  | Global.LightBlue.LightBlue-300  | `#75CEFF` | rgb(117, 206, 255) |
| Alias.Semantic2-200  | Light Blue 200  | Global.LightBlue.LightBlue-200  | `#BDE8FF` | rgb(189, 232, 255) |
| Alias.Semantic2-100  | Light Blue 100  | Global.LightBlue.LightBlue-100  | `#DEF5FF` | rgb(222, 245, 255) |

### Purple Palette (Secondary Actions)

| Token                | Alias       | Global Reference          | Hex       | RGB                |
| -------------------- | ----------- | ------------------------- | --------- | ------------------ |
| Alias.Secondary-1000 | Purple 1000 | Global.Purple.Purple-1000 | `#393999` | rgb(57, 57, 153)   |
| Alias.Secondary-900  | Purple 900  | Global.Purple.Purple-900  | `#3939B5` | rgb(57, 57, 181)   |
| Alias.Secondary-800  | Purple 800  | Global.Purple.Purple-800  | `#4641C4` | rgb(70, 65, 196)   |
| Alias.Secondary-700  | Purple 700  | Global.Purple.Purple-700  | `#5348D3` | rgb(83, 72, 211)   |
| Alias.Secondary-600  | Purple 600  | Global.Purple.Purple-600  | `#6150E1` | rgb(97, 80, 225)   |
| Alias.Secondary-500  | Purple 500  | Global.Purple.Purple-500  | `#7B5FFF` | rgb(123, 95, 225)  |
| Alias.Secondary-400  | Purple 400  | Global.Purple.Purple-400  | `#9781FF` | rgb(151, 129, 255) |
| Alias.Secondary-300  | Purple 300  | Global.Purple.Purple-300  | `#BBADFF` | rgb(187, 173, 255) |
| Alias.Secondary-200  | Purple 200  | Global.Purple.Purple-200  | `#DBD3FF` | rgb(219, 211, 255) |
| Alias.Secondary-100  | Purple 100  | Global.Purple.Purple-100  | `#F2EBFF` | rgb(242, 235, 255) |

## 3. Tertiary Colors (Semantic 3)

Màu thứ ba được sử dụng cho các accent và highlight.

### Turquoise Palette

| Token                | Alias          | Global Reference                | Hex       | RGB                |
| -------------------- | -------------- | ------------------------------- | --------- | ------------------ |
| Alias.Semantic3-1000 | Turquoise 1000 | Global.Turquoise.Turquoise-1000 | `#045654` | rgb(4, 86, 84)     |
| Alias.Semantic3-900  | Turquoise 900  | Global.Turquoise.Turquoise-900  | `#076968` | rgb(7, 105, 104)   |
| Alias.Semantic3-800  | Turquoise 800  | Global.Turquoise.Turquoise-800  | `#0A7D7C` | rgb(10, 125, 124)  |
| Alias.Semantic3-700  | Turquoise 700  | Global.Turquoise.Turquoise-700  | `#0C908F` | rgb(12, 144, 143)  |
| Alias.Semantic3-600  | Turquoise 600  | Global.Turquoise.Turquoise-600  | `#0FA4A3` | rgb(15, 164, 163)  |
| Alias.Semantic3-500  | Turquoise 500  | Global.Turquoise.Turquoise-500  | `#12B7B7` | rgb(18, 183, 183)  |
| Alias.Semantic3-400  | Turquoise 400  | Global.Turquoise.Turquoise-400  | `#52DDDD` | rgb(82, 221, 221)  |
| Alias.Semantic3-300  | Turquoise 300  | Global.Turquoise.Turquoise-300  | `#A2F5F5` | rgb(162, 245, 245) |
| Alias.Semantic3-200  | Turquoise 200  | Global.Turquoise.Turquoise-200  | `#D4FBFB` | rgb(212, 251, 251) |
| Alias.Semantic3-100  | Turquoise 100  | Global.Turquoise.Turquoise-100  | `#E7FEFE` | rgb(231, 254, 254) |

## 4. Status Colors

### Success Colors

| Token              | Alias      | Global Reference        | Hex       | RGB                |
| ------------------ | ---------- | ----------------------- | --------- | ------------------ |
| Alias.Success-1000 | Green 1000 | Global.Green.Green-1000 | `#01633C` | rgb(1, 99, 60)     |
| Alias.Success-900  | Green 900  | Global.Green.Green-900  | `#017245` | rgb(1, 114, 69)    |
| Alias.Success-800  | Green 800  | Global.Green.Green-800  | `#01814E` | rgb(1, 129, 78)    |
| Alias.Success-700  | Green 700  | Global.Green.Green-700  | `#008F56` | rgb(0, 143, 86)    |
| Alias.Success-600  | Green 600  | Global.Green.Green-600  | `#009E5F` | rgb(0, 158, 95)    |
| Alias.Success-500  | Green 500  | Global.Green.Green-500  | `#00AD68` | rgb(0, 173, 104)   |
| Alias.Success-400  | Green 400  | Global.Green.Green-400  | `#3DD196` | rgb(61, 209, 150)  |
| Alias.Success-300  | Green 300  | Global.Green.Green-300  | `#85F1C6` | rgb(133, 241, 198) |
| Alias.Success-200  | Green 200  | Global.Green.Green-200  | `#CDFCE9` | rgb(205, 252, 233) |
| Alias.Success-100  | Green 100  | Global.Green.Green-100  | `#E6F6EC` | rgb(230, 246, 236) |

### Alert Colors

| Token            | Alias       | Global Reference          | Hex       | RGB                |
| ---------------- | ----------- | ------------------------- | --------- | ------------------ |
| Alias.Alert-1000 | Orange 1000 | Global.Orange.Orange-1000 | `#703A00` | rgb(112, 58, 0)    |
| Alias.Alert-900  | Orange 900  | Global.Orange.Orange-900  | `#8C4A00` | rgb(140, 74, 0)    |
| Alias.Alert-800  | Orange 800  | Global.Orange.Orange-800  | `#A75A00` | rgb(167, 90, 0)    |
| Alias.Alert-700  | Orange 700  | Global.Orange.Orange-700  | `#C36A00` | rgb(195, 106, 0)   |
| Alias.Alert-600  | Orange 600  | Global.Orange.Orange-600  | `#DE7A00` | rgb(222, 122, 0)   |
| Alias.Alert-500  | Orange 500  | Global.Orange.Orange-500  | `#FA8A00` | rgb(250, 138, 0)   |
| Alias.Alert-400  | Orange 400  | Global.Orange.Orange-400  | `#F9A500` | rgb(249, 165, 0)   |
| Alias.Alert-300  | Orange 300  | Global.Orange.Orange-300  | `#FDC64E` | rgb(253, 198, 78)  |
| Alias.Alert-200  | Orange 200  | Global.Orange.Orange-200  | `#FBDE97` | rgb(251, 222, 151) |
| Alias.Alert-100  | Orange 100  | Global.Orange.Orange-100  | `#FFF4D0` | rgb(255, 244, 208) |

### Error Colors

| Token            | Alias    | Global Reference    | Hex       | RGB                |
| ---------------- | -------- | ------------------- | --------- | ------------------ |
| Alias.Error-1000 | Red 1000 | Global.Red.Red-1000 | `#6B0B08` | rgb(107, 11, 8)    |
| Alias.Error-900  | Red 900  | Global.Red.Red-900  | `#860906` | rgb(134, 9, 6)     |
| Alias.Error-800  | Red 800  | Global.Red.Red-800  | `#A00705` | rgb(160, 7, 5)     |
| Alias.Error-700  | Red 700  | Global.Red.Red-700  | `#BB0403` | rgb(187, 4, 3)     |
| Alias.Error-600  | Red 600  | Global.Red.Red-600  | `#D50202` | rgb(213, 2, 2)     |
| Alias.Error-500  | Red 500  | Global.Red.Red-500  | `#F00000` | rgb(245, 0, 0)     |
| Alias.Error-400  | Red 400  | Global.Red.Red-400  | `#F34343` | rgb(243, 67, 67)   |
| Alias.Error-300  | Red 300  | Global.Red.Red-300  | `#FB7070` | rgb(251, 112, 112) |
| Alias.Error-200  | Red 200  | Global.Red.Red-200  | `#FFB1B1` | rgb(255, 177, 177) |
| Alias.Error-100  | Red 100  | Global.Red.Red-100  | `#FFE3E3` | rgb(255, 227, 227) |

## 5. Neutral Colors (Grayscale)

Màu trung tính được sử dụng cho text, borders, backgrounds và các element interface cơ bản.

| Token                  | Alias              | Global Reference                    | Hex                        | RGB                      |
| ---------------------- | ------------------ | ----------------------------------- | -------------------------- | ------------------------ |
| Alias.Neutral-1000     | Grayscale 1000     | Global.Grayscale.Grayscale-1000     | `#000000`                  | rgb(0, 0, 0)             |
| Alias.Neutral-1000/50% | Grayscale 1000 50% | Global.Grayscale.Grayscale-1000/50% | `rgba(0, 0, 0, 0.5)`       | rgba(0, 0, 0, 0.5)       |
| Alias.Neutral-900      | Grayscale 900      | Global.Grayscale.Grayscale-900      | `#2E2E2E`                  | rgb(46, 46, 46)          |
| Alias.Neutral-800      | Grayscale 800      | Global.Grayscale.Grayscale-800      | `#4D4D4D`                  | rgb(77, 77, 77)          |
| Alias.Neutral-700      | Grayscale 700      | Global.Grayscale.Grayscale-700      | `#646464`                  | rgb(100, 100, 100)       |
| Alias.Neutral-600      | Grayscale 600      | Global.Grayscale.Grayscale-600      | `#808080`                  | rgb(128, 128, 128)       |
| Alias.Neutral-500      | Grayscale 500      | Global.Grayscale.Grayscale-500      | `#9B9B9B`                  | rgb(155, 155, 155)       |
| Alias.Neutral-400      | Grayscale 400      | Global.Grayscale.Grayscale-400      | `#CCCCCC`                  | rgb(204, 204, 204)       |
| Alias.Neutral-300      | Grayscale 300      | Global.Grayscale.Grayscale-300      | `#D8D8D8`                  | rgb(216, 216, 216)       |
| Alias.Neutral-200      | Grayscale 200      | Global.Grayscale.Grayscale-200      | `#F3F3F3`                  | rgb(243, 243, 243)       |
| Alias.Neutral-100      | Grayscale 100      | Global.Grayscale.Grayscale-100      | `#FFFFFF`                  | rgb(255, 255, 255)       |
| Alias.Neutral-100/50%  | Grayscale 100 50%  | Global.Grayscale.Grayscale-100/50%  | `rgba(255, 255, 255, 0.5)` | rgba(255, 255, 255, 0.5) |

## 6. Additional Colors & Gradients

### Additional 1 (Gradient 1)

- **Token**: `Alias.Additional1-500`
- **Global Reference**: `Global.Gradient1.Gradient1-500`
- **Gradient**: `linear-gradient(-15deg, rgba(229, 209, 255, 1) 0%, rgba(221, 231, 255, 1) 6%, rgba(245, 255, 253, 1) 57%, rgba(255, 255, 255, 1) 100%)`

### Additional 2 (Gradient 2)

| Token                 | Weight | Gradient                                                                         |
| --------------------- | ------ | -------------------------------------------------------------------------------- |
| Alias.Additional2-500 | 500    | `linear-gradient(90deg, #141ED2 0%, #0075FF 100%)`                               |
| Alias.Additional2-400 | 400    | `linear-gradient(90deg, rgba(57, 73, 214, 1) 0%, rgba(44, 135, 246, 1) 100%)`    |
| Alias.Additional2-300 | 300    | `linear-gradient(90deg, rgba(96, 106, 213, 1) 0%, rgba(86, 154, 237, 1) 100%)`   |
| Alias.Additional2-200 | 200    | `linear-gradient(90deg, rgba(136, 140, 212, 1) 0%, rgba(128, 174, 229, 1) 100%)` |
| Alias.Additional2-100 | 100    | `linear-gradient(90deg, rgba(174, 177, 212, 1) 0%, rgba(170, 193, 221, 1) 100%)` |

### Additional 3 (Gradient 3)

- **Token**: `Alias.Additional3-500`
- **Global Reference**: `Global.Gradient3.Gradient3-500`
- **Gradient**: `linear-gradient(235deg, rgba(189, 213, 228, 0.7) 0%, rgba(214, 234, 240, 0) 100%)`

### Additional 4 (Gradient 4)

- **Token**: `Alias.Additional4-500`
- **Global Reference**: `Global.Gradient4.Gradient4-500`
- **Gradient**: `linear-gradient(180deg, #141ED2 0%, #0075FF 100%)`

## Hướng dẫn sử dụng

### 1. Semantic Naming

Luôn sử dụng alias tokens thay vì global tokens để đảm bảo tính semantic và khả năng thay đổi theme dễ dàng.

**Tốt:**

```css
color: var(--alias-primary-500);
background-color: var(--alias-success-100);
```

**Không nên:**

```css
color: var(--global-blue-blue-500);
background-color: var(--global-green-green-100);
```

### 2. Contrast & Accessibility

- Sử dụng các weight cao hơn (700-1000) cho text trên background sáng
- Sử dụng các weight thấp hơn (100-400) cho backgrounds và subtle elements
- Luôn kiểm tra contrast ratio để đảm bảo accessibility

### 3. Color Usage Guidelines

- **Primary**: Sử dụng cho CTAs chính, links quan trọng
- **Secondary**: Sử dụng cho actions phụ, navigation
- **Success**: Thông báo thành công, trạng thái tích cực
- **Alert**: Cảnh báo, thông tin cần chú ý
- **Error**: Lỗi, trạng thái nguy hiểm
- **Neutral**: Text, borders, backgrounds, dividers

### 4. Implementation

Các token này có thể được implement thông qua:

- CSS Custom Properties (CSS Variables)
- SCSS/SASS variables
- Design tokens trong các framework như Styled Components
- Theme objects trong các UI libraries

---

_Tài liệu này được tạo từ Design System V2 (Web) trên Figma và sẽ được cập nhật khi có thay đổi trong design system._
