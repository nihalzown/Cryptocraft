# 🔐 CryptoCraft

**CryptoCraft** is a modern web application that bridges the gap between complex cryptographic mathematics and user-friendly design. It features a sleek dark-themed interface and allows users to encrypt text using a variety of algorithms—ranging from ancient historical ciphers to modern military-grade standards.

## ✨ Features

- 🎨 **Modern Dark UI**: Beautiful dark-themed interface with green accent colors
- 🔄 **Real-time Processing**: Instant encryption with live feedback
- ⚡ **Multiple Algorithms**: Support for classical and modern encryption methods
- 🛡️ **Error Handling**: Clear error messages and validation
- 📱 **Responsive Design**: Works seamlessly across different screen sizes
- 🔗 **RESTful API**: Clean backend API for easy integration

## 🏗️ Architecture

CryptoCraft follows a **decoupled architecture** where the frontend and backend are separate projects that communicate via REST APIs:

- **Backend (The Brain) 🧠**: Java Spring Boot application handling encryption logic
- **Frontend (The Face) 🎨**: Next.js React application providing the user interface

## 🔧 Technologies Used

### Backend
- **Language**: Java 17
- **Framework**: Spring Boot 3.4.12
- **Build Tool**: Maven
- **Dependencies**: Spring Web, Spring Boot Test

### Frontend
- **Framework**: Next.js 16.0.3
- **Runtime**: React 19.2.0
- **Styling**: Tailwind CSS 4
- **Linting**: ESLint with Next.js config

## 🔐 Supported Encryption Algorithms

### Classical Ciphers (Educational)
1. **Caesar Cipher**: Shifts letters by a fixed number of positions ✅
2. **Substitution Cipher**: Replaces each letter with another letter based on a key ✅
3. **Playfair Cipher**: Encrypts pairs of letters using a 5×5 grid ✅

### Modern Standards (Secure)
1. **DES (Data Encryption Standard)**: Legacy symmetric encryption algorithm ✅
2. **AES (Advanced Encryption Standard)**: Modern symmetric encryption 🚧
3. **Blowfish**: Variable-length key block cipher 🚧
4. **RC4**: Stream cipher algorithm 🚧

> ✅ = Fully Implemented | 🚧 = Frontend Ready (Backend Implementation Pending)

## 📁 Project Structure

```
CryptoCraft/
├── backend/                 # Spring Boot backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/cryptocraft/backend/
│   │   │   │       ├── BackendApplication.java
│   │   │   │       ├── CryptoController.java
│   │   │   │       ├── EncryptionRequest.java
│   │   │   │       └── EncryptionService.java
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   ├── pom.xml
│   └── mvnw / mvnw.cmd
├── frontend/                # Next.js frontend
│   ├── src/
│   │   └── app/
│   │       ├── page.js
│   │       ├── layout.js
│   │       └── globals.css
│   ├── public/
│   ├── package.json
│   └── next.config.mjs
└── README.md
```

## 🚀 Getting Started

### Prerequisites

- **Java 17** or higher
- **Node.js 18** or higher
- **npm** or **yarn**

### Backend Setup

1. **Navigate to the backend directory**:
   ```bash
   cd backend
   ```

2. **Run the Spring Boot application**:
   ```bash
   # Using Maven wrapper (recommended)
   ./mvnw spring-boot:run
   
   # Or using Maven (if installed globally)
   mvn spring-boot:run
   ```

3. **Verify the backend is running**:
   - The application will start on `http://localhost:8080`
   - Check the console for the startup message

### Frontend Setup

1. **Navigate to the frontend directory**:
   ```bash
   cd frontend
   ```

2. **Install dependencies**:
   ```bash
   npm install
   ```

3. **Start the development server**:
   ```bash
   npm run dev
   ```

4. **Access the application**:
   - Open your browser and navigate to `http://localhost:3000`
   - You should see the CryptoCraft interface with a dark theme and green accents

### Application Status Indicators

- ✅ **Backend Running**: API accessible at http://localhost:8080
- ✅ **Frontend Running**: UI accessible at http://localhost:3000
- 🔄 **Connected**: Frontend successfully communicates with backend
- ❌ **Error Handling**: Clear error messages if backend is unavailable

## 🔗 API Endpoints

The backend provides the following REST API endpoint:

### Encrypt Text
- **URL**: `POST /api/encrypt`
- **Content-Type**: `application/json`

#### Request Body
```json
{
  "algorithm": "caesar|substitution|playfair|des",
  "plaintext": "Your text to encrypt",
  "key": "Your encryption key"
}
```

#### Response
```json
{
  "result": "Encrypted text"
}
```

#### Error Response
```json
{
  "error": "Error message"
}
```

### Example API Calls

**Caesar Cipher:**
```bash
curl -X POST http://localhost:8080/api/encrypt \
  -H "Content-Type: application/json" \
  -d '{"algorithm": "caesar", "plaintext": "Hello World", "key": "3"}'
```

**Substitution Cipher:**
```bash
curl -X POST http://localhost:8080/api/encrypt \
  -H "Content-Type: application/json" \
  -d '{"algorithm": "substitution", "plaintext": "Hello", "key": "QWERTYUIOPASDFGHJKLZXCVBNM"}'
```

**Playfair Cipher:**
```bash
curl -X POST http://localhost:8080/api/encrypt \
  -H "Content-Type: application/json" \
  -d '{"algorithm": "playfair", "plaintext": "Hello World", "key": "SECRET"}'
```

**DES Encryption:**
```bash
curl -X POST http://localhost:8080/api/encrypt \
  -H "Content-Type: application/json" \
  -d '{"algorithm": "des", "plaintext": "Hello World", "key": "secretkey"}'
```

## 🧪 Testing

### Backend Testing
```bash
cd backend
./mvnw test
```

### Frontend Testing
```bash
cd frontend
npm run lint
```

## 🔨 Development Scripts

### Backend
```bash
# Start development server
./mvnw spring-boot:run

# Run tests
./mvnw test

# Build for production
./mvnw package
```

### Frontend
```bash
# Start development server
npm run dev

# Build for production
npm run build

# Start production server
npm start

# Run linting
npm run lint
```

## 🎯 User Flow

1. **Select**: User chooses an encryption algorithm from dropdown
2. **Input**: User enters message in textarea and secret key in text field
3. **Validate**: Frontend validates required fields before submission
4. **Process**: Frontend sends JSON request to backend API at `/api/encrypt`
5. **Response**: Backend processes encryption and returns result
6. **Display**: Encrypted text appears in styled green code block
7. **Error Handling**: Any errors are displayed in red alert box

## 🖥️ User Interface

### Main Interface Components
- **Header**: Large "🛡️ CryptoCraft" title with green accent
- **Algorithm Selector**: Dropdown with 7 encryption options
- **Message Input**: Large textarea for text to encrypt
- **Key Input**: Text field for encryption key/password
- **Encrypt Button**: Green "Encrypt Now 🔒" call-to-action
- **Results Display**: Styled output area with monospace font
- **Error Display**: Red-themed error messages when needed

### Design Features
- **Color Scheme**: Dark gray background with green accents
- **Typography**: Clean fonts with monospace for encrypted output
- **Responsive**: Centered layout that works on all screen sizes
- **Feedback**: Visual feedback for form interactions and states

## 🔧 Configuration

### Backend Configuration
Edit `backend/src/main/resources/application.properties`:
```properties
spring.application.name=backend
server.port=8080
```

### Frontend Configuration
Edit `frontend/next.config.mjs` for Next.js specific configurations.

### API Integration
The frontend automatically connects to the backend at `http://localhost:8080/api/encrypt`. The application includes:
- **Automatic Error Handling**: Displays connection errors if backend is down
- **Form Validation**: Ensures all required fields are filled
- **Response Processing**: Handles both success and error responses
- **Loading States**: Visual feedback during API calls


## 📝 License

This project is for educational purposes. Feel free to use and modify as needed.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test your changes
5. Submit a pull request

## 📞 Support

If you encounter any issues or have questions, please create an issue in the repository.

---

**Happy Encrypting! 🔐**