# CryptoCraft 🛡️

A web application for encrypting text using various cryptographic algorithms.

---

## Stage 1: Core Encryption Engine - COMPLETE ✅

This initial stage focuses on building the core encryption logic as a standalone Python script. The goal was to create a reliable function that can securely encrypt data using the AES standard.

### Current Features

-   Encrypts a hardcoded string using **AES** in **CBC mode**.
-   Derives a secure, fixed-length key from a secret password using **SHA-256**.
-   Packages the necessary **IV (Initialization Vector)** and ciphertext into a transport-safe **Base64** string.

### How to Run This Stage

To test the functionality of this stage:

1.  **Clone the repository:**
    ```bash
    git clone <your-repo-url>
    cd cryptocraft-backend
    ```

2.  **Create and activate the virtual environment:**
    ```bash
    python -m venv venv
    source venv/bin/activate
    ```

3.  **Install dependencies:**
    *(Before installing, it's good practice to create a `requirements.txt` file)*
    ```bash
    pip freeze > requirements.txt
    ```
    *(Now, install from the file)*
    ```bash
    pip install -r requirements.txt
    ```

4.  **Run the script:**
    ```bash
    python encryption_service.py
    ```

You will see the test output printed directly to the terminal, showing the original text and the final encrypted Base64 string.

### Technology Used

-   Python 3
-   `pycryptodome`