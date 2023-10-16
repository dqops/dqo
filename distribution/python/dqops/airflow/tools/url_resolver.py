import re

def extract_base_url(url: str) -> str:
    """
    Extracts base url form the url provided in the argument.

    It fixes urls for python api which expects a base url address built from protocol, host name and a trailing slash.

    Parameters
    ----------
    url : str
        The url

    Returns
    -------
    str
        The base url with the trailing slash.
    """

    base_url: str = re.search("(^.+?[^\/:](?=[?\/]|$))", url).group()

    return base_url + "/"
