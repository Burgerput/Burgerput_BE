import { useState } from "react";

export function useDisplay() {
  const [isOutletHidden, setIsOutletHidden] = useState(true);
  const [hiddenCount, setHiddenCount] = useState(0);

  const handleHidden = () => {
    setIsOutletHidden(true);
  };

  const handleVisible = () => {
    setIsOutletHidden(false);
  };

  const handleCount = () => {
    setHiddenCount((prev) => prev + 1);
  };

  return {
    handleHidden,
    handleVisible,
    handleCount,
    isOutletHidden,
    hiddenCount,
  };
}
