# Angular Material với Design System V2 (Web)

Tài liệu này hướng dẫn cách sử dụng Angular Material components tuân thủ theo color tokens và typography tokens đã được định nghĩa trong design system.

## Mục lục

1. [Thiết lập Design Tokens](#thiết-lập-design-tokens)
2. [CSS Custom Properties](#css-custom-properties)
3. [Angular Material Theme Configuration](#angular-material-theme-configuration)
4. [Component Usage Examples](#component-usage-examples)
5. [Best Practices](#best-practices)

## Thiết lập Design Tokens

### 1. CSS Custom Properties cho Colors

Tạo file `src/styles/_design-tokens.scss`:

```scss
// Design System Color Tokens
:root {
  // Primary Colors (Semantic 1 - Dark Blue)
  --color-semantic1-1000: #192d39;
  --color-semantic1-900: #273b4b;
  --color-semantic1-800: #354a5e;
  --color-semantic1-700: #435870;
  --color-semantic1-600: #516682;
  --color-semantic1-500: #6d83a7;
  --color-semantic1-400: #9bafc8;
  --color-semantic1-300: #cadbe8;
  --color-semantic1-200: #ecf5fa;
  --color-semantic1-100: #f9fdff;

  // Primary Colors (Blue)
  --color-primary-1000: #00087c;
  --color-primary-900: #050d8c;
  --color-primary-800: #09129b;
  --color-primary-700: #0e16ab;
  --color-primary-600: #121bbb;
  --color-primary-500: #141ed2;
  --color-primary-400: #5f7eeb;
  --color-primary-300: #a3b7fd;
  --color-primary-200: #dae4ff;
  --color-primary-100: #e9efff;

  // Secondary Colors (Light Blue)
  --color-semantic2-1000: #003e7c;
  --color-semantic2-900: #004996;
  --color-semantic2-800: #0054b0;
  --color-semantic2-700: #005fcb;
  --color-semantic2-600: #006ae5;
  --color-semantic2-500: #0075ff;
  --color-semantic2-400: #00a5ff;
  --color-semantic2-300: #75ceff;
  --color-semantic2-200: #bde8ff;
  --color-semantic2-100: #def5ff;

  // Secondary Colors (Purple)
  --color-secondary-1000: #393999;
  --color-secondary-900: #3939b5;
  --color-secondary-800: #4641c4;
  --color-secondary-700: #5348d3;
  --color-secondary-600: #6150e1;
  --color-secondary-500: #7b5fff;
  --color-secondary-400: #9781ff;
  --color-secondary-300: #bbadff;
  --color-secondary-200: #dbd3ff;
  --color-secondary-100: #f2ebff;

  // Status Colors
  --color-success-500: #00ad68;
  --color-success-400: #3dd196;
  --color-success-300: #85f1c6;
  --color-success-200: #cdfce9;
  --color-success-100: #e6f6ec;

  --color-alert-500: #fa8a00;
  --color-alert-400: #f9a500;
  --color-alert-300: #fdc64e;
  --color-alert-200: #fbde97;
  --color-alert-100: #fff4d0;

  --color-error-500: #f00000;
  --color-error-400: #f34343;
  --color-error-300: #fb7070;
  --color-error-200: #ffb1b1;
  --color-error-100: #ffe3e3;

  // Neutral Colors
  --color-neutral-1000: #000000;
  --color-neutral-900: #2e2e2e;
  --color-neutral-800: #4d4d4d;
  --color-neutral-700: #646464;
  --color-neutral-600: #808080;
  --color-neutral-500: #9b9b9b;
  --color-neutral-400: #cccccc;
  --color-neutral-300: #d8d8d8;
  --color-neutral-200: #f3f3f3;
  --color-neutral-100: #ffffff;

  // Gradients
  --gradient-additional1: linear-gradient(
    -15deg,
    rgba(229, 209, 255, 1) 0%,
    rgba(221, 231, 255, 1) 6%,
    rgba(245, 255, 253, 1) 57%,
    rgba(255, 255, 255, 1) 100%
  );
  --gradient-additional2-500: linear-gradient(90deg, #141ed2 0%, #0075ff 100%);
  --gradient-additional3: linear-gradient(235deg, rgba(189, 213, 228, 0.7) 0%, rgba(214, 234, 240, 0) 100%);
  --gradient-additional4: linear-gradient(180deg, #141ed2 0%, #0075ff 100%);
}
```

### 2. CSS Custom Properties cho Typography

Thêm vào file `src/styles/_design-tokens.scss`:

```scss
:root {
  // Font Family
  --font-family-primary: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;

  // Display Typography
  --typography-display-xl-bold: 700 72px/88px var(--font-family-primary);
  --typography-display-l-bold: 700 60px/72px var(--font-family-primary);
  --typography-display-m-bold: 700 48px/56px var(--font-family-primary);
  --typography-display-s-bold: 700 36px/44px var(--font-family-primary);

  // Heading Typography
  --typography-heading-xl-bold: 700 30px/38px var(--font-family-primary);
  --typography-heading-xl-semibold: 600 30px/38px var(--font-family-primary);
  --typography-heading-l-bold: 700 24px/32px var(--font-family-primary);
  --typography-heading-l-semibold: 600 24px/32px var(--font-family-primary);
  --typography-heading-m-bold: 700 20px/28px var(--font-family-primary);
  --typography-heading-m-semibold: 600 20px/28px var(--font-family-primary);
  --typography-heading-s-bold: 700 18px/24px var(--font-family-primary);
  --typography-heading-s-semibold: 600 18px/24px var(--font-family-primary);
  --typography-heading-xs-semibold: 600 16px/20px var(--font-family-primary);

  // Body Typography
  --typography-body-xl-regular: 400 20px/30px var(--font-family-primary);
  --typography-body-l-regular: 400 18px/28px var(--font-family-primary);
  --typography-body-m-regular: 400 16px/24px var(--font-family-primary);
  --typography-body-m-medium: 500 16px/24px var(--font-family-primary);
  --typography-body-s-regular: 400 14px/20px var(--font-family-primary);
  --typography-body-s-medium: 500 14px/20px var(--font-family-primary);
  --typography-body-xs-regular: 400 12px/16px var(--font-family-primary);

  // Caption Typography
  --typography-caption-l-regular: 400 14px/20px var(--font-family-primary);
  --typography-caption-m-regular: 400 12px/16px var(--font-family-primary);
  --typography-caption-s-regular: 400 11px/16px var(--font-family-primary);

  // Overline Typography
  --typography-overline-m-semibold: 600 12px/16px var(--font-family-primary);
  --typography-overline-s-semibold: 600 11px/16px var(--font-family-primary);
}
```

## Angular Material Theme Configuration

### 1. Tạo Custom Theme

Tạo file `src/styles/_material-theme.scss`:

```scss
@use '@angular/material' as mat;

// Import design tokens
@import 'design-tokens';

// Define custom palettes using design tokens
$primary-palette: (
  50: var(--color-primary-100),
  100: var(--color-primary-200),
  200: var(--color-primary-300),
  300: var(--color-primary-400),
  400: var(--color-primary-500),
  500: var(--color-primary-600),
  600: var(--color-primary-700),
  700: var(--color-primary-800),
  800: var(--color-primary-900),
  900: var(--color-primary-1000),
  A100: var(--color-primary-200),
  A200: var(--color-primary-300),
  A400: var(--color-primary-500),
  A700: var(--color-primary-700),
  contrast: (
    50: var(--color-neutral-1000),
    100: var(--color-neutral-1000),
    200: var(--color-neutral-1000),
    300: var(--color-neutral-1000),
    400: var(--color-neutral-100),
    500: var(--color-neutral-100),
    600: var(--color-neutral-100),
    700: var(--color-neutral-100),
    800: var(--color-neutral-100),
    900: var(--color-neutral-100),
    A100: var(--color-neutral-1000),
    A200: var(--color-neutral-1000),
    A400: var(--color-neutral-100),
    A700: var(--color-neutral-100),
  ),
);

$accent-palette: (
  50: var(--color-secondary-100),
  100: var(--color-secondary-200),
  200: var(--color-secondary-300),
  300: var(--color-secondary-400),
  400: var(--color-secondary-500),
  500: var(--color-secondary-600),
  600: var(--color-secondary-700),
  700: var(--color-secondary-800),
  800: var(--color-secondary-900),
  900: var(--color-secondary-1000),
  A100: var(--color-secondary-200),
  A200: var(--color-secondary-300),
  A400: var(--color-secondary-500),
  A700: var(--color-secondary-700),
  contrast: (
    50: var(--color-neutral-1000),
    100: var(--color-neutral-1000),
    200: var(--color-neutral-1000),
    300: var(--color-neutral-1000),
    400: var(--color-neutral-100),
    500: var(--color-neutral-100),
    600: var(--color-neutral-100),
    700: var(--color-neutral-100),
    800: var(--color-neutral-100),
    900: var(--color-neutral-100),
    A100: var(--color-neutral-1000),
    A200: var(--color-neutral-1000),
    A400: var(--color-neutral-100),
    A700: var(--color-neutral-100),
  ),
);

// Create theme
$primary: mat.define-palette($primary-palette, 500);
$accent: mat.define-palette($accent-palette, 500);
$warn: mat.define-palette(mat.$red-palette);

$theme: mat.define-light-theme(
  (
    color: (
      primary: $primary,
      accent: $accent,
      warn: $warn,
    ),
    typography: mat.define-typography-config(
        $font-family: var(--font-family-primary),
      ),
    density: 0,
  )
);

// Apply the theme
@include mat.all-component-themes($theme);
```

### 2. Import trong styles.scss

```scss
// src/styles.scss
@import 'styles/design-tokens';
@import 'styles/material-theme';

// Additional custom styles
@import 'styles/components';
```

## Component Usage Examples

### 1. Buttons

```html
<!-- Primary Button -->
<button mat-raised-button color="primary" class="btn-primary">Primary Action</button>

<!-- Secondary Button -->
<button mat-raised-button color="accent" class="btn-secondary">Secondary Action</button>

<!-- Text Button -->
<button mat-button class="btn-text">Text Button</button>
```

```scss
// Component styles
.btn-primary {
  font: var(--typography-body-m-medium);
  letter-spacing: 0em;
}

.btn-secondary {
  font: var(--typography-body-m-medium);
  letter-spacing: 0em;
}

.btn-text {
  font: var(--typography-body-m-regular);
  color: var(--color-primary-500);
}
```

### 2. Cards

```html
<mat-card class="custom-card">
  <mat-card-header>
    <mat-card-title class="card-title">Card Title</mat-card-title>
    <mat-card-subtitle class="card-subtitle">Card Subtitle</mat-card-subtitle>
  </mat-card-header>

  <mat-card-content class="card-content">
    <p>Card content goes here with proper typography.</p>
  </mat-card-content>

  <mat-card-actions>
    <button mat-button color="primary">ACTION 1</button>
    <button mat-button color="accent">ACTION 2</button>
  </mat-card-actions>
</mat-card>
```

```scss
.custom-card {
  border: 1px solid var(--color-neutral-300);
  border-radius: 8px;
  box-shadow: 0px 1px 3px rgba(0, 0, 0, 0.12);
}

.card-title {
  font: var(--typography-heading-l-semibold);
  color: var(--color-neutral-1000);
  margin-bottom: 4px;
}

.card-subtitle {
  font: var(--typography-body-s-medium);
  color: var(--color-neutral-700);
}

.card-content {
  font: var(--typography-body-m-regular);
  color: var(--color-neutral-900);
  line-height: 1.5;
}
```

### 3. Form Fields

```html
<mat-form-field class="custom-form-field" appearance="outline">
  <mat-label class="form-label">Label</mat-label>
  <input matInput placeholder="Placeholder text" class="form-input" />
  <mat-hint class="form-hint">Helper text</mat-hint>
  <mat-error class="form-error">Error message</mat-error>
</mat-form-field>
```

```scss
.custom-form-field {
  width: 100%;

  .mat-mdc-form-field-outline {
    color: var(--color-neutral-400);
  }

  &.mat-focused .mat-mdc-form-field-outline-thick {
    color: var(--color-primary-500);
  }
}

.form-label {
  font: var(--typography-heading-s-semibold);
  color: var(--color-neutral-800);
}

.form-input {
  font: var(--typography-body-m-regular);
  color: var(--color-neutral-1000);
}

.form-hint {
  font: var(--typography-caption-m-regular);
  color: var(--color-neutral-600);
}

.form-error {
  font: var(--typography-caption-m-regular);
  color: var(--color-error-500);
}
```

### 4. Navigation

```html
<mat-toolbar class="custom-toolbar">
  <span class="toolbar-title">Application Title</span>
  <span class="spacer"></span>
  <button mat-icon-button>
    <mat-icon>menu</mat-icon>
  </button>
</mat-toolbar>

<mat-sidenav-container class="sidenav-container">
  <mat-sidenav class="sidenav">
    <mat-nav-list>
      <a mat-list-item routerLink="/dashboard" class="nav-item">
        <mat-icon matListIcon>dashboard</mat-icon>
        <span class="nav-text">Dashboard</span>
      </a>
      <a mat-list-item routerLink="/users" class="nav-item">
        <mat-icon matListIcon>people</mat-icon>
        <span class="nav-text">Users</span>
      </a>
    </mat-nav-list>
  </mat-sidenav>

  <mat-sidenav-content>
    <router-outlet></router-outlet>
  </mat-sidenav-content>
</mat-sidenav-container>
```

```scss
.custom-toolbar {
  background: var(--gradient-additional2-500);
  color: var(--color-neutral-100);
}

.toolbar-title {
  font: var(--typography-heading-m-semibold);
}

.sidenav {
  width: 280px;
  background: var(--color-neutral-100);
  border-right: 1px solid var(--color-neutral-300);
}

.nav-item {
  font: var(--typography-body-m-medium);
  color: var(--color-neutral-800);

  &.mat-mdc-list-item-with-one-line {
    height: 48px;
  }

  &:hover {
    background-color: var(--color-primary-100);
  }

  &.active {
    background-color: var(--color-primary-200);
    color: var(--color-primary-700);
  }
}

.nav-text {
  margin-left: 16px;
}

.spacer {
  flex: 1 1 auto;
}
```

### 5. Tables

```html
<mat-table [dataSource]="dataSource" class="custom-table">
  <!-- Header Column -->
  <ng-container matColumnDef="name">
    <mat-header-cell *matHeaderCellDef class="table-header">Name</mat-header-cell>
    <mat-cell *matCellDef="let element" class="table-cell">{{element.name}}</mat-cell>
  </ng-container>

  <!-- Status Column -->
  <ng-container matColumnDef="status">
    <mat-header-cell *matHeaderCellDef class="table-header">Status</mat-header-cell>
    <mat-cell *matCellDef="let element" class="table-cell">
      <span class="status-badge" [ngClass]="'status-' + element.status.toLowerCase()"> {{element.status}} </span>
    </mat-cell>
  </ng-container>

  <mat-header-row *matHeaderRowDef="displayedColumns"></mat-header-row>
  <mat-row *matRowDef="let row; columns: displayedColumns;"></mat-row>
</mat-table>
```

```scss
.custom-table {
  width: 100%;
  border: 1px solid var(--color-neutral-300);
  border-radius: 8px;
  overflow: hidden;
}

.table-header {
  font: var(--typography-heading-s-semibold);
  color: var(--color-neutral-1000);
  background-color: var(--color-neutral-200);
  border-bottom: 1px solid var(--color-neutral-300);
}

.table-cell {
  font: var(--typography-body-s-regular);
  color: var(--color-neutral-900);
  border-bottom: 1px solid var(--color-neutral-200);
}

.status-badge {
  padding: 4px 8px;
  border-radius: 4px;
  font: var(--typography-caption-s-semibold);
  text-transform: uppercase;

  &.status-active {
    background-color: var(--color-success-200);
    color: var(--color-success-500);
  }

  &.status-pending {
    background-color: var(--color-alert-200);
    color: var(--color-alert-500);
  }

  &.status-inactive {
    background-color: var(--color-error-200);
    color: var(--color-error-500);
  }
}
```

### 6. Snackbar & Dialogs

```typescript
// Component TypeScript
openSnackBar(message: string, type: 'success' | 'error' | 'warning' = 'success') {
  this.snackBar.open(message, 'Close', {
    duration: 5000,
    panelClass: [`snackbar-${type}`]
  });
}
```

```scss
// Global styles for snackbars
.snackbar-success {
  background-color: var(--color-success-500) !important;
  color: var(--color-neutral-100) !important;

  .mat-mdc-snack-bar-label {
    font: var(--typography-body-s-medium) !important;
  }
}

.snackbar-error {
  background-color: var(--color-error-500) !important;
  color: var(--color-neutral-100) !important;

  .mat-mdc-snack-bar-label {
    font: var(--typography-body-s-medium) !important;
  }
}

.snackbar-warning {
  background-color: var(--color-alert-500) !important;
  color: var(--color-neutral-100) !important;

  .mat-mdc-snack-bar-label {
    font: var(--typography-body-s-medium) !important;
  }
}
```

## Best Practices

### 1. Consistency Guidelines

- **Luôn sử dụng design tokens**: Không hardcode colors hoặc font values
- **Semantic naming**: Sử dụng semantic color names (primary, success, error) thay vì specific colors
- **Typography hierarchy**: Tuân thủ strict typography scale đã định nghĩa

### 2. Component Customization

```scss
// ✅ Tốt - Sử dụng design tokens
.custom-button {
  background-color: var(--color-primary-500);
  color: var(--color-neutral-100);
  font: var(--typography-body-m-medium);
}

// ❌ Không nên - Hardcode values
.custom-button {
  background-color: #141ed2;
  color: white;
  font-size: 16px;
  font-weight: 500;
}
```

### 3. Responsive Design

```scss
// Mobile-first approach với typography scaling
.hero-title {
  font: var(--typography-display-s-bold);

  @media (min-width: 768px) {
    font: var(--typography-display-m-bold);
  }

  @media (min-width: 1024px) {
    font: var(--typography-display-l-bold);
  }

  @media (min-width: 1440px) {
    font: var(--typography-display-xl-bold);
  }
}
```

### 4. Accessibility

```scss
// Đảm bảo contrast ratio
.text-on-primary {
  color: var(--color-neutral-100);
  background-color: var(--color-primary-500);
}

.text-on-light {
  color: var(--color-neutral-1000);
  background-color: var(--color-neutral-100);
}

// Focus states
.focusable-element:focus {
  outline: 2px solid var(--color-primary-500);
  outline-offset: 2px;
}
```

### 5. Theme Switching

```scss
// Support cho dark theme (tương lai)
[data-theme='dark'] {
  --color-neutral-1000: #ffffff;
  --color-neutral-100: #000000;
  --color-neutral-900: #f3f3f3;
  // ... other dark theme overrides
}
```

### 6. Performance Tips

- **Lazy load Material modules**: Chỉ import các modules cần thiết
- **Tree shaking**: Sử dụng individual imports
- **CSS Custom Properties**: Tận dụng native CSS variables cho better performance

```typescript
// ✅ Tốt - Individual imports
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';

// ❌ Không nên - Import toàn bộ
import { MaterialModule } from '@angular/material';
```

## Kết luận

Việc tuân thủ design tokens khi sử dụng Angular Material giúp:

1. **Consistency**: Đảm bảo UI/UX nhất quán across toàn bộ application
2. **Maintainability**: Dễ dàng update colors/typography từ một nơi central
3. **Scalability**: Hỗ trợ theme switching và responsive design
4. **Accessibility**: Đảm bảo contrast ratios và typography scales phù hợp
5. **Developer Experience**: Code dễ đọc và maintain hơn

Luôn nhớ test các components trên different screen sizes và với different themes để đảm bảo consistency và accessibility.

---

_Tài liệu này được tạo dựa trên Design System V2 (Web) và sẽ được cập nhật khi có thay đổi trong design system._
