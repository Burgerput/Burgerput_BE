import { useNavigate, useOutletContext } from "react-router-dom";

export function useGoHome() {
  const { handleHidden } = useOutletContext();
  const navigate = useNavigate();

  const handleClick = () => {
    handleHidden();
    navigate("/");
  };

  return { handleClick };
}
