export function useModal({ setResult }) {
  const close = () => {
    setResult(false);
  };

  const confirm = () => {
    setResult(false);
  };

  return { close, confirm };
}
