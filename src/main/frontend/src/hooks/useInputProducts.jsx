import { useCallback, useRef, useState } from "react";
import { useDebounce } from "./useDebounce";

export function useInputProducts({ min, max }) {
  const inputContainer = useRef();
  const [temp, setTemp] = useState("");
  const [warning, setWarning] = useState(false);
  const [missing, setMissing] = useState(false);

  const handleChange = (e) => {
    setTemp(e.target.value);
    setValue(e.target.value);
  };

  const setValue = useCallback(
    useDebounce((value) => validateRange(value), 1300),
    []
  );

  const validateRange = (value) => {
    if (parseInt(value) > parseInt(max) || parseInt(value) < parseInt(min)) {
      setWarning(true);
      setTemp("");
    } else {
      setWarning(false);
    }
  };

  const handleClick = () => {
    if (missing) {
      setTemp("");
      setMissing((prev) => !prev);
      return;
    }
    setTemp("999");
    setMissing((prev) => !prev);
  };

  return { temp, warning, handleChange, handleClick, missing, inputContainer };
}
