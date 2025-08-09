from Crypto.Cipher import AES
from Crypto.Util.Padding import pad, unpad
from Crypto.Hash import SHA256
import base64

import re
import numpy as np


# modern ciphers

def encrypt_aes(plaintext: str, key: str) -> str:
    try:
        plaintext_bytes = plaintext.encode('utf-8')
        key_bytes = key.encode('utf-8')
        hasher = SHA256.new(key_bytes)
        aes_key = hasher.digest()[:16]
        cipher = AES.new(aes_key, AES.MODE_CBC)
        ciphertext = cipher.encrypt(pad(plaintext_bytes, AES.block_size))
        iv = cipher.iv
        encrypted_package = base64.b64encode(iv + ciphertext)
        return encrypted_package.decode('utf-8')
    except Exception as e:
        print(f"Encryption failed: {e}")
        return None
    
#classic ciphers

def encrypt_caesar(plaintext: str, shift: int) -> str:
    encrypted =""
    for char in plaintext:
        if char.isupper():
            encrypted += chr((ord(char) - ord('A') + shift) % 26 + ord('A'))
        elif char.islower():
            encrypted += chr((ord(char) - ord('a') + shift) % 26 + ord('a'))
        else:
            encrypted += char
    return encrypted


def encrypt_substitution(plaintext: str, key: str) -> str:
    alphabet = 'abcdefghijklmnopqrstuvwxyz'
    if len(key) != 26 or not key.isalpha() or len(set(key.lower())) != 26:
        return "Invalid substitution key"
    
    key_map = str.maketrans(alphabet + alphabet.upper(), key.lower() + key.upper())
    return plaintext.translate(key_map)


def encrypt_railfence(plaintext: str, key: int) -> str:
    if key <= 1:
        return plaintext
    
    fence = [[] for _ in range(key)]
    direction = 1
    rail = 0
    for char in plaintext:
        fence[rail].append(char)
        rail += direction
        if rail == 0 or rail == key - 1:
            direction *= -1
    
    return "".join([''.join(row) for row in fence])


def encrypt_playfair(plaintext: str, key: str) -> str:
    def generate_table(key):
        key = re.sub(r'[^A-Z]', '', key.upper().replace('J', 'I'))
        alphabet = 'ABCDEFGHIKLMNOPQRSTUVWXYZ'
        table_chars = ""
        for char in key + alphabet:
            if char not in table_chars:
                table_chars += char
        return [table_chars[i:i + 5] for i in range(0,25,5)]
    
    def find_position(char, table):
        for i, row in enumerate(table):
            if char in row:
                return i, row.find(char)
        return -1, -1




if __name__ == "__main__":
    print("Testing encryption service...")
    myplaintext = "i am a secret message"
    mysecretkey = "my_secret_key"
    encrypted = encrypt_aes(myplaintext, mysecretkey)
    print(f"Secret key used: {mysecretkey}")
    if encrypted:
        print(f"Encrypted message: {encrypted}")
    else:
        print("Encryption failed.")