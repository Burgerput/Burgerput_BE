export function useRandomTemp() {
  /**
   * 지정된 최소, 최대 온도 범위를 이용해 랜덤한 온도를 제출하는 함수
   * @param {Object[]} products
   * @returns 랜덤한 온도가 저장된 Products 배열
   */
  const generateRandomTemp = (products) => {
    let randProducts = [];
    products.forEach(({ id, name, min, max }) => {
      const temp = rand(Number(min), Number(max));

      randProducts.push({ id, name, temp });
    });

    return randProducts;
  };

  /**
   * 최소, 최대값을 받아 해당 범위 내의 랜덤 값을 리턴하는 함수
   * @param {Number} min
   * @param {Number} max
   * @returns 해당 범위 내의 랜덤값
   */
  const rand = (min, max) => {
    const random = Math.floor(Math.random() * (max - min + 1)) + min;

    return random;
  };

  return { generateRandomTemp };
}
