"use client";
import { useState } from "react";

export default function Home(){
  const [formData, setFormdata]=useState({
    algorithm: "caesar",
    plaintext: "",
    key: ""
  });

  const [result, setResult] = useState("");
  const [error, setError] = useState("");

  const handleChange = (e) => {
    setFormdata({ ...formData,[e.target.name]: e.target.value });
  };

  const handleSubmit = async(e) =>{
    e.preventDefault();
    setError("");
    setResult("");

    try{
      const response = await fetch("http://localhost:8080/api/encrypt" , {
        method: "POST",
        headers:{
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });
      
      const data =await response.jason();

      if(data.error){
        setError(data.error);
      } else{
        setResult(data.result);
      }
    } catch(err){
      setError("Failed to connect to backend. Is it running?");
    }
  };

  return (
    <div className="min-h-screen bg-gray-900 text-white flex flex-col items-center justify-center p-4">
      <h1 className="text-4xl font-bold mb-8 text-green-400">🛡️ CryptoCraft</h1>

      <div className="bg-gray-800 p-8 rounded-lg shadow-lg w-full max-w-md border border-gray-700">
        <from onSubmit={handleSubmit} className="space-y-4">
          {/*ALgorithm selecting */}
          <div>
            <label className="block text-sm font-medium mb-1">ALgorithm</label>
            <select
              name="algorithm"
              value={formData.algorithm}
              onChange={handleChange}
              className="w-full p-2 rounded bg-gray-700 border border-gray-600 focus:outline-none focus:border-green-500"
              >
                <option value="caesar">Caesar Cipher</option>
                <option value="substitution">Substitution Cipher</option>
                <option value="playfair">Playfair Cipher</option>
                <option value="des">DES Cipher</option>
              </select>
          </div>
          {/*Plaintext input*/}
          <div>
            <label className="block text-sm font-medium mb-1">Message</label>
            <textarea
              name="plaintext"
              value={formData.plaintext}
              onChange={handleChange}
              placeholder="Enter text to encrypt..."
              className="w-full p-2 rounded bg-gray-700 border border-gray-600 focus:outline-none focus:border-green-500 h-24"
              required
            />
          </div>
          {/*key input*/}
          <div>
            <label className="block text-sm font-medium mb-1">Secret key</label>
            <input
              type="text"
              name="key"
              value={formData.key}
              onChange={handleChange}
              placeholder="e.g, 3 for Caesar, or a password"
              className="w-full p-2 rounded bg-gray-700 border border-gray-600 focus:outline-none focus:border-green-500"
              required
            />
          </div>
          <button
            type="submit"
            className="w-full bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-4 rounded transition duration-200"
          >
            Encrypt Now 🔒
          </button>
        </from>


        {/* Results Section */}
        {error && (
          <div className="mt-4 p-3 bg-red-900/50 border border-red-500 text-red-200 rounded">
            ❌ {error}
          </div>
        )}

        {result && (
            <div className="mt-6">
            <h3 className="text-lg font-semibold mb-2">Encrypted Result:</h3>
            <div className="p-4 bg-gray-900 rounded border border-green-500/30 break-all font-mono text-green-300">
              {result}
            </div>
          </div>
        )}
      </div>
    </div>
  );
}