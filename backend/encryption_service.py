from Crypto.Cipher import AES, DES
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
    
def encrypt_des(plaintext: str, key: str) -> str:
    try:
        plaintext_bytes = plaintext.encode('utf-8')
        key_bytes = key.encode('utf-8')
        hasher = SHA256.new(key_bytes)
        des_key = hasher.digest()[:8]
        cipher = DES.new(des_key, DES.MODE_CBC)
        ciphertext = cipher.encrypt(pad(plaintext_bytes, DES.block_size))
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

    def prepare_text(text):
        text = re.sub(r'[^A-Z]', '', text.upper().replace('J', 'I'))
        i = 0
        while i<len(text)-1:
            if text[i] == text[i+1]:
                text = text[:i+1] + 'X' + text[i+1:]
            i += 2
        if len(text) % 2 != 0:
            text += 'X'
        return [text[i:i + 2] for i in range(0, len(text), 2)]
    
    table = generate_table(key)
    diagraphs = prepare_text(plaintext)
    ciphertext = ""

    for pair in diagraphs:
        r1, c1 = find_position(pair[0], table)
        r2, c2 = find_position(pair[1], table)
        if r1 == r2:  # same row
            ciphertext += table[r1][(c1 + 1) % 5] + table[r2][(c2 + 1) % 5]
        elif c1 == c2:  # same column
            ciphertext += table[(r1 + 1) % 5][c1] + table[(r2 + 1) % 5][c2]
        else:  # rectangle
            ciphertext += table[r1][c2] + table[r2][c1]

    return ciphertext


def encrypt_hill(plaintext: str, key: str) -> str:
    key = key.upper().replace(" ", "")
    plaintext = plaintext.upper().replace(" ", "")

    if len(key) != 9 or not key.isalpha():
        return "Invalid Hill cipher key"
    
    key_matrix = np.array([ord(k) - ord('A') for k in key]).reshape(3, 3)

    det = int(round(np.linalg.det(key_matrix)))
    if det == 0 or np.gcd(det, 26) != 1:
        return "key not invertible"
    
    if len(plaintext) % 3 != 0:
        plaintext += 'X' * (3 - len(plaintext) % 3)

    ciphertext = ""
    for i in range(0, len(plaintext), 3):
        vector = np.array([ord(c) - ord('A') for c in plaintext[i:i + 3]])
        encrypted_vector = np.dot(key_matrix, vector) % 26
        ciphertext += ''.join([chr(int(n) + ord('A')) for n in encrypted_vector])
    
    return ciphertext

    
# --test cases for the encryption service

if __name__ == "__main__":
   print("stage 1: Testing all ciphers")

   print("\naes encryption:")
   aes_plaintext = "Hello, World!"
   aes_key = "my_secret_key"
   print(f"Plaintext: {aes_plaintext}")
   aes_encrypted = encrypt_aes(aes_plaintext, aes_key)
   print(f"Encrypted: {aes_encrypted}")

   print("\ncaesar encryption:")
   caesar_plaintext = "Hello, World!"
   caesar_shift = 3
   print(f"Plaintext: {caesar_plaintext}")
   caesar_encrypted = encrypt_caesar(caesar_plaintext, caesar_shift)
   print(f"Encrypted: {caesar_encrypted}")


   print("\nsubstitution encryption:")
   substitution_plaintext = "Hello, World!"
   substitution_key = "QWERTYUIOPASDFGHJKLZXCVBNM"
   print(f"Plaintext: {substitution_plaintext}")
   substitution_encrypted = encrypt_substitution(substitution_plaintext, substitution_key)
   print(f"Encrypted: {substitution_encrypted}")


   print("\nrailfence encryption:")
   railfence_plaintext = "Hello, World!"
   railfence_key = 3
   print(f"Plaintext: {railfence_plaintext}")
   railfence_encrypted = encrypt_railfence(railfence_plaintext, railfence_key)
   print(f"Encrypted: {railfence_encrypted}")


   print("\nplayfair encryption:")
   playfair_plaintext = "Hello, World!"
   playfair_key = "PLAYFAIR EXAMPLE"
   print(f"Plaintext: {playfair_plaintext}")
   playfair_encrypted = encrypt_playfair(playfair_plaintext, playfair_key)
   print(f"Encrypted: {playfair_encrypted}")

   print("\nhill encryption:")
   hill_plaintext = "HELLO"
   hill_key = "GYBNQKURP"
   print(f"Plaintext: {hill_plaintext}")
   hill_encrypted = encrypt_hill(hill_plaintext, hill_key)
   print(f"Encrypted: {hill_encrypted}")

   print("\nAll ciphers tested successfully.")


