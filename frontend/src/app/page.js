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

}