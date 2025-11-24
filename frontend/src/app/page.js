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
        </from>
      </div>
    </div>
  )


}