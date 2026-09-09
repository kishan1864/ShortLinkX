# ShortLinkX Frontend
React + Vite frontend for the ShortLinkX Spring Boot backend.

Run:
```bash
npm install
npm run dev
```
Open http://localhost:5173

Backend defaults to http://localhost:8080. Optional `.env`:
`VITE_API_URL=http://localhost:8080`

Implemented:
- Animated pastel gradient background
- Collapsible sidebar
- Auth login/register connected to backend
- JWT added automatically to API requests
- Dashboard
- Create URL connected to POST /api/urls
- URL history UI
- Analytics UI with animated chart
- QR code generation/download
- Profile/logout
- Toasts and modern glass UI

Note: history and analytics use demo presentation data until the corresponding backend APIs are added.