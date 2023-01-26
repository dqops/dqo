
export const convertArrayToObject = (arr: [string, string][]) => {
  return arr.reduce((a, el) => el[0] ? ({ ...a, [el[0]]: el[1] }) : a, {});
};

export const convertObjectToArray = (obj: any): any => {
  return Object.entries(obj || {});
}