# CryptoCraft 🛡️

A web application for encrypting text using various cryptographic algorithms.

---

## Stage 1: Core Encryption Engine - COMPLETE ✅

This initial stage focuses on building the core encryption logic for both modern and classical ciphers in a standalone Python script.

### Current Features

-   **AES Encryption:** A modern, secure symmetric encryption standard.
-   **DES Encryption:** A legacy symmetric encryption standard.
-   **Classical Ciphers Implemented:**
    -   Caesar Cipher
    -   Simple Substitution Cipher
    -   Rail Fence Cipher
    -   Playfair Cipher
    -   Hill Cipher (3x3)

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
    ```bash
    pip install -r requirements.txt
    ```
    *(If you don't have a `requirements.txt` file yet, create one with `pip freeze > requirements.txt` after installing the libraries.)*

4.  **Run the script:**
    ```bash
    python encryption_service.py
    ```

You will see the test output for all implemented ciphers printed to the terminal.

### Technology Used

-   Python 3
-   `pycryptodome` (for AES & DES)
-   `numpy` (for Hill Cipher)